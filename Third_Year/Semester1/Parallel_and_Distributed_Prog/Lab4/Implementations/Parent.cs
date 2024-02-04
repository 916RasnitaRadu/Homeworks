using Lab4.Socket;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4.Implementations
{
    internal abstract class Parent
    {
        private List<String> Urls { get; }

        protected abstract string ParserType { get; }

        protected Parent(List<string> urls)
        {
            Urls = urls;
            Run();
        }

        protected abstract void Run();

        protected void ForEach(Action<int, string> action)
        {
            var count = 0;
            Urls.ForEach(url => action(count++, url));
        }

        protected List<T> Map<T>(Func<int, string, T> mapper)
        {
            var count = 0;
            return Urls.Select(url => mapper(count++, url)).ToList();
        }

        protected void logConnected(SocketController socket)
        {
            Console.WriteLine($"{ParserType} - {socket.Id}: Socket connected to {socket.BaseUrl} ({socket.UrlPath})");
        }

        protected void logSent(SocketController socket, int nrOfSentBytes)
        {
            Console.WriteLine($"{ParserType} - {socket.Id}: Sent {nrOfSentBytes} bytes to the server");
        }

        protected void logReceive(SocketController socket)
        {
            Console.WriteLine($"{ParserType} - {socket.Id}: Received:\n\n {socket.GetResponseContent}");
        }
    }
}
