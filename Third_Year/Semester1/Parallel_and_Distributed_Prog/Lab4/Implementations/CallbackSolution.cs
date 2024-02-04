using Lab4.Socket;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab4.Implementations
{
    internal class CallbackSolution : Parent
    {
        protected override string ParserType => "Callback";

        public CallbackSolution(List<string> urls) : base (urls) { }

        protected override void Run()
        {
            ForEach((index, url) => Start(SocketController.create(url, index))); // create a socket for each url and apply the Start() function
        }

        private void Start(SocketController socket)
        {
            socket.BeginConnect(HandleConnected); // it calls BeginConnect() function and it sends the callback function HandleConnected to it
            do
            {
                Thread.Sleep(100);
            } while (socket.Connected);
        }

        private void HandleConnected(SocketController socket)
        {
            logConnected(socket);
            socket.BeginSend(HandleSent);
        }

        private void HandleSent(SocketController socket, int nrOfBytes)
        {
            logSent(socket, nrOfBytes);
            socket.BeginReceive(HandleReceived);
        }

        private void HandleReceived(SocketController socketController) 
        {
            logReceive(socketController);
            socketController.ShutdownAndClose();
        } 
    }
}
