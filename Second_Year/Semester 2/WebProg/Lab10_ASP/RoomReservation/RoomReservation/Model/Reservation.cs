namespace RoomReservation.Model
{
    public class Reservation
    {
        public int id { get; set; }

        public int roomID { get; set; }

        public string check_in { get; set; }

        public string check_out { get; set; }
    }
}
