using calendar_api.Data;
using calendar_api.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Diagnostics;
using System.Timers;

namespace calendar_api.Controllers
{
    [Route("api/ticket")]
    [ApiController]
    public class TicketsController : ControllerBase
    {
        private readonly CalendarAPIContext _context;

        public TicketsController(CalendarAPIContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Ticket>>> GetTickets()
        {
            if (_context.Tickets == null)
            {
                return NotFound();
            }

            return await _context.Tickets.ToListAsync();
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Ticket>> GetTicket(long id)
        {
            if (_context.Tickets == null)
            {
                return NotFound();
            }
            var ticket = await _context.Tickets.FindAsync(id);

            if (ticket == null)
            {
                return NotFound();
            }

            return ticket;
        }

        [HttpGet("calendar/{id}")]
        public async Task<ActionResult<IEnumerable<Ticket>>> GetTicketsByCalendarId(long id)
        {
            var tickets = new List<Ticket>();

            IEnumerable<Ticket> ticketsLinq = from ticket in _context.Tickets where ticket.CalendarId == id select ticket;
            foreach (Ticket ticket in ticketsLinq)
            {
                tickets.Add(ticket);
            }

            return tickets;
        }

        [HttpGet("type/calendar/{id}")]
        public async Task<ActionResult<IEnumerable<TicketType>>> GetTicketTypesByCalendarId(long id)
        {
            var ticketTypes = new List<TicketType>();

            foreach (var ticketType in _context.TicketTypes)
            {
                if (ticketType.CalendarId == id)
                {
                    ticketTypes.Add(ticketType);
                }
            }

            return ticketTypes;
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutTicket(long id, Ticket ticket)
        {
            if (id != ticket.Id)
            {
                return BadRequest();
            }

            _context.Entry(ticket).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TicketExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        [HttpPost]
        public async Task<ActionResult<Ticket>> PostTicket(Ticket ticket)
        {
            if (_context.Tickets == null)
            {
                return Problem("Entity set 'CalendarAPIContext.Tickets'  is null.");
            }

            var ticketTypes = await _context.TicketTypes.ToListAsync();
            if (!ticketTypes.Any(ticketType => ticketType.Name.Equals(ticket.Type)))
            {
                _context.TicketTypes.Add(new TicketType() { Name = ticket.Type, CalendarId = ticket.CalendarId });
            }

            _context.Tickets.Add(ticket);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetTicket", new { id = ticket.Id }, ticket);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTicket(long id)
        {
            if (_context.Tickets == null)
            {
                return NotFound();
            }
            var ticket = await _context.Tickets.FindAsync(id);
            if (ticket == null)
            {
                return NotFound();
            }

            _context.Tickets.Remove(ticket);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool TicketExists(long id)
        {
            return (_context.Tickets?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
