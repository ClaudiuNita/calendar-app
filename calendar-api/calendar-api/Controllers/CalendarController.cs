using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using calendar_api.Models;
using calendar_api.Data;

namespace calendar_api.Controllers
{
    [Route("api/calendar")]
    [ApiController]
    public class CalendarController : ControllerBase
    {
        private readonly CalendarAPIContext _context;

        public CalendarController(CalendarAPIContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Calendar>>> GetCalendars()
        {
            if (_context.Calendars == null)
            {
                return NotFound();
            }
            return await _context.Calendars.ToListAsync();
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Calendar>> GetCalendar(long id)
        {
            if (_context.Calendars == null)
            {
                return NotFound();
            }
            var calendar = await _context.Calendars.FindAsync(id);

            if (calendar == null)
            {
                return NotFound();
            }

            return calendar;
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutCalendar(long id, Calendar calendar)
        {
            if (id != calendar.Id)
            {
                return BadRequest();
            }

            _context.Entry(calendar).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CalendarExists(id))
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
        public async Task<ActionResult<Calendar>> PostCalendar(Calendar calendar)
        {
            if (_context.Calendars == null)
            {
                return Problem("Entity set 'CalendarAPIContext.Calendars' is null.");
            }
            _context.Calendars.Add(calendar);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCalendar", new { id = calendar.Id }, calendar);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCalendar(long id)
        {
            if (_context.Calendars == null)
            {
                return NotFound();
            }
            var calendar = await _context.Calendars.FindAsync(id);
            if (calendar == null)
            {
                return NotFound();
            }

            _context.Calendars.Remove(calendar);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool CalendarExists(long id)
        {
            return (_context.Calendars?.Any(e => e.Id == id)).GetValueOrDefault();
        }
    }
}
