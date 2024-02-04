using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Lab4.Socket
{
    internal class SocketController : System.Net.Sockets.Socket
    {
        public int Id { get; }

        public string BaseUrl { get; }

        public string UrlPath { get; }

        private IPEndPoint EndPoint { get; }

        private StringBuilder ResponseBuilder { get; }

        private const int DefaultHttpPort = 80;
        private const int BufferSize = 1024;

        public static SocketController create(string url, int id)
        {
            var index = url.IndexOf('/');
            var baseUrl = index < 0 ? url : url.Substring(0, index);
            var urlPath = index < 0 ? "/" : url.Substring(index);

            var ipHostInfo = Dns.GetHostEntry(baseUrl);
            var ipAddress = ipHostInfo.AddressList[0];

            return new SocketController(baseUrl, urlPath, ipAddress, id);
        }

        private SocketController(string baseUrl, string urlPath, IPAddress iPAddress, int id)
            : base(iPAddress.AddressFamily, System.Net.Sockets.SocketType.Stream, ProtocolType.Tcp)
        {
            Id = id;
            BaseUrl = baseUrl;
            UrlPath = urlPath;
            EndPoint = new IPEndPoint(iPAddress, DefaultHttpPort);
            ResponseBuilder = new StringBuilder();
        }

        // USING CALLBACK
 
        public void BeginConnect(Action<SocketController> onConnected)
        {
            BeginConnect(EndPoint, asyncResult =>
            {
                EndConnect(asyncResult);
                onConnected(this); // HandleConnected(socket) this == socket
            }, null);
        }

        public void BeginSend(Action<SocketController, int> onSent)
        {
            var stringToSend = $"GET {UrlPath} HTTP/1.1\r\n" +
                $"Host: {BaseUrl}\r\n" +
                "Content-Length: 0\r\n\r\n";
            var encodedString = Encoding.ASCII.GetBytes(stringToSend);

            BeginSend(encodedString, 0, encodedString.Length, SocketFlags.None, asyncResult =>
            {
                var numberOfSentBytes = EndSend(asyncResult);
                onSent(this, numberOfSentBytes); // HandleSend(socket)
            }, null);
        }

        public void BeginReceive(Action<SocketController> onReceived)
        {
            var buffer = new byte[BufferSize];
            ResponseBuilder.Clear();

            BeginReceive(buffer, 0, BufferSize, SocketFlags.None, asyncResult => 
                            HandleReceiveResult(asyncResult, buffer, onReceived), null);
        }

        // USING TASK

        public Task BeginConnectAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource<object>(); // represent asynchronous operation

            BeginConnect(_ => { taskCompletion.TrySetResult(null); }); // calls respective function and sets the result

            return taskCompletion.Task; // return the task
        });

        public Task<int> BeginSendAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource<int>();

            BeginSend((_, numberOfSentBytes) => taskCompletion.TrySetResult(numberOfSentBytes));

            return taskCompletion.Task;
        });

        public Task BeginReceiveAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource<object>();

            BeginReceive(_ => taskCompletion.TrySetResult(null));

            return taskCompletion.Task;
        });

        public void ShutdownAndClose()
        {
            Shutdown(SocketShutdown.Both);
            Close();
        }

        public string GetResponseContent => ResponseBuilder.ToString();

        private void HandleReceiveResult(
            IAsyncResult asyncResult,
            byte[] buffer,
            Action<SocketController> onReceived)
        {
            var numberOfReadBytes = EndReceive(asyncResult);
            ResponseBuilder.Append(Encoding.ASCII.GetString(buffer, 0, numberOfReadBytes));
            if (!ResponseBuilder.ToString().Contains("</html>"))
            {
                BeginReceive(buffer, 0, BufferSize, SocketFlags.None, asyncResult2 => HandleReceiveResult(asyncResult2, buffer, onReceived), null);
                return;
            }

            onReceived(this);
        }
    }
}
