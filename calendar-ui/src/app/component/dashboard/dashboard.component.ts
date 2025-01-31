import { Component } from '@angular/core';
import { Calendar } from '../../model/Calendar';
import { CalendarService } from '../../service/calendar/calendar.service';
import { CommonModule, DatePipe, NgFor } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NgFor, DatePipe, CommonModule, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent { 
  
  calendars: Calendar[] = [];

  constructor(private calendarService: CalendarService) {}

  ngOnInit() {
    this.getAllCalendars();
  }

  getCalendarById(id: number) {    
    this.calendarService.getCalendarById(id).subscribe(
      // () => window.location.reload()
    )
  }

  getAllCalendars() {
    this.calendarService.getAllCalendars().subscribe(
      calendars => this.calendars = calendars
    )
  }

  deleteCalendar(id: number) {    
    this.calendarService.deleteCalendar(id).subscribe(
      () => window.location.reload()
    )
  }
}
