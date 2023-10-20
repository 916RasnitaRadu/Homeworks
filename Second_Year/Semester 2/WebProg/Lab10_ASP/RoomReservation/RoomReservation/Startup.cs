using Microsoft.AspNetCore.Mvc;

namespace RoomReservation
{
    public class Startup : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
