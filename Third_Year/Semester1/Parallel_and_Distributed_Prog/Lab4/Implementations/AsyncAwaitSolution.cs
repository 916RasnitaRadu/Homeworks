using Lab4.Socket;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4.Implementations
{
    internal class AsyncAwaitSolution : Parent
    {
        protected override string ParserType => "Async/Await";

        public AsyncAwaitSolution(List<string> urls) : base(urls) { }

        protected override void Run()
        {
            var tasks = Map((index, url) => Task.Run(() =>
                       Start(SocketController.create(url, index))));

            Task.WhenAll(tasks).Wait();
        }

        private async Task Start(SocketController socket)
        {
            await socket.BeginConnectAsync();
            logConnected(socket);

            var nrOfBytes = await socket.BeginSendAsync();
            logSent(socket, nrOfBytes);

            await socket.BeginReceiveAsync();
            logReceive(socket);

            socket.ShutdownAndClose();
        }
    }
}
