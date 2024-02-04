using Lab4.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4
{
    internal class Program
    {
        private static readonly List<string> Urls = new List<string>() {
          "www.dspcluj.ro/HTML/CORONAVIRUS/incidenta.html",
            "www.cnatdcu.ro/" 
        }; 
    
    


        static void Main(string[] args)
        {
            Console.WriteLine("1. Callback");
            Console.WriteLine("2. Task");
            Console.WriteLine("3. Async / Await");

            bool ok = true;
            while (ok)
            {
                string choice = Console.ReadLine();

                switch (choice)
                {
                    case "1":
                        new CallbackSolution(Urls);
                        break;
                    case "2":
                        new TaskSolution(Urls);
                        break;
                    case "3":
                        new AsyncAwaitSolution(Urls);
                        break;
                    case "gata":
                        ok = false;
                        break;
                    default:
                        Console.WriteLine("Invalid input!");
                        break;
                }
            } 
            

            
        }
    }
}
