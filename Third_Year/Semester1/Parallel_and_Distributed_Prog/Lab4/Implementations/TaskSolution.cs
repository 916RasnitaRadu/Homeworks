using Lab4.Socket;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4.Implementations
{
    internal class TaskSolution : Parent
    {
        protected override string ParserType => "Task";

        public TaskSolution(List<string> urls) : base(urls) { }

        protected override void Run()
        {
            var tasks = Map((index, url) => Task.Run(() => // initialize a list of tasks and runs
                    Start(SocketController.create(url, index))));

            Task.WhenAll(tasks).Wait();
        }

        private Task Start(SocketController socket)
        {
            socket.BeginConnectAsync().Wait();
            logConnected(socket);

            var sendTask = socket.BeginSendAsync();
            sendTask.Wait();
            var nrOfBytes = sendTask.Result;
            logSent(socket, nrOfBytes);

            socket.BeginReceiveAsync().Wait();
            logReceive(socket);

            socket.ShutdownAndClose();
            return Task.CompletedTask;
        } 
    }
}
