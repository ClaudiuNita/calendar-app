namespace calendar_api.Models
{
    public class Calendar
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public DateTime Created { get; set; } = DateTime.Now;
        public long OwnerId { get; set; }
     }
}
