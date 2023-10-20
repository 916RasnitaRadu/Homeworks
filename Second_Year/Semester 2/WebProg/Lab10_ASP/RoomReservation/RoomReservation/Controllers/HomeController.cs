using Microsoft.AspNetCore.Mvc;
using RoomReservation.DataAbstractionLayer;
using RoomReservation.Model;

namespace RoomReservation.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private DBManager dBManager = new DBManager();

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        [HttpPost]
        public IActionResult LoginUser([FromBody] User user)
        {
            List<User> users = dBManager.GetUsers();
            User toReturn = null;
            foreach (User u in users)
                if (user.username == u.username && user.password == u.password)
                    toReturn = u;
            return Ok(toReturn);
        }

        [HttpPost("postReservation")]
        public IActionResult addReservation([FromBody] Reservation reservation)
        {
            Reservation res = this.dBManager.addReservation(reservation);

            return Ok(res);
        }

        [HttpDelete("cancelReservation/{id}")]
        public void deleteReservation(int id)
        {
            this.dBManager.deleteReservation(id);
            Console.WriteLine(id);
        } 
    }
}
