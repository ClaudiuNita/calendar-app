
using Microsoft.EntityFrameworkCore;
using calendar_api.Controllers;
using calendar_api.Models;
using calendar_api.Data;

namespace calendar_api
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            // Add services to the container.

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddDbContext<CalendarAPIContext>(opt => opt.UseInMemoryDatabase("CalendarDB"));

            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseHttpsRedirection();

            app.UseAuthorization();


            app.MapControllers();

            using (var scope = app.Services.CreateScope())
            {
                SeedData(scope.ServiceProvider.GetRequiredService<CalendarAPIContext>());
            }

            app.Run();
        }

        public static void SeedData(CalendarAPIContext context)
        {
            context.Calendars.AddRange(
                new Calendar { Id = 1, Name = "Team", OwnerId = 1 },
                new Calendar { Id = 2, Name = "Tasks", OwnerId = 2 }
            );

            context.Users.AddRange(
                new User { Id = 1, Username = "User1", FullName = "Claudiu Nita", Email = "caludiunita@" },
                new User { Id = 2, Username = "User2", FullName = "Claudiu Nita", Email = "caludiunita@" }
            );

            context.Tickets.AddRange(
                new Ticket { Id = 1, Name = "Task1", Type = "task", OwnerId = 1, CalendarId = 1},
                new Ticket { Id = 2, Name = "Task2", Type = "event", OwnerId = 2, CalendarId = 2 }
            );

            context.SaveChanges();
        }
    }
}