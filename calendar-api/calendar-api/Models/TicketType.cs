namespace calendar_api.Models
{
    public class TicketType
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public long CalendarId { get; set; }
    }
}
