import { Component, OnInit } from '@angular/core';
import { Calendar } from '../../model/Calendar';
import { FormsModule } from '@angular/forms';
import { CalendarService } from '../../service/calendar/calendar.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-calendar-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './calendar-form.component.html',
  styleUrl: './calendar-form.component.css'
})
export class CalendarFormComponent implements OnInit {

  calendar: Calendar = {
    id: 0,
    name: '',
    created: new Date(),
    ownerId: 1
  };
  hasId: boolean = false;

  constructor(private calendarService: CalendarService,
              private router: Router,
              private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.has('id')) {
      this.hasId = true;
      const id = parseInt(this.route.snapshot.paramMap.get('id')!);
      
      this.calendarService.getCalendarById(id).subscribe(
        calendar => { this.calendar = calendar }
      )
    }
  }

  saveCalendar() {
    this.calendarService.saveCalendar(this.calendar).subscribe(
      () => this.router.navigateByUrl('/dashboard')
    );
  }

  updateCalendar() {
    this.calendarService.updateCalendar(this.calendar).subscribe(
      () => this.router.navigateByUrl('/dashboard')
    );
  }
}
