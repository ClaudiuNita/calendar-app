using calendar_api.Models;
using Microsoft.EntityFrameworkCore;

namespace calendar_api.Data
{
    public class CalendarAPIContext : DbContext
    {
        public CalendarAPIContext(DbContextOptions<CalendarAPIContext> options) : base(options)
        {
        }

        public DbSet<Calendar> Calendars { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Ticket> Tickets { get; set; }
        public DbSet<TicketType> TicketTypes { get; set; }
    }
}
