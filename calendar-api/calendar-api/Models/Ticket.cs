namespace calendar_api.Models
{
    public class Ticket
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public string? Description { get; set; }
        public DateTime? DateStart {  get; set; }
        public DateTime? DateEnd { get; set; }
        public DateTime Created { get; set; } = DateTime.Now;
        public string Type { get; set; }
        public long OwnerId { get; set; }
        public long CalendarId { get; set; }
    }
}
