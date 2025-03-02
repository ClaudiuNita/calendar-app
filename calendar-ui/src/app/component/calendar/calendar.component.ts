import { CommonModule, DatePipe, JsonPipe, NgClass, NgFor, NgTemplateOutlet } from '@angular/common';
import {
  Component,
  computed,
  input,
  output,
  signal,
  TemplateRef,
} from '@angular/core';
import {
  addMonths,
  eachDayOfInterval,
  endOfMonth,
  format,
  isEqual,
  startOfMonth,
  startOfToday,
  subMonths,
} from 'date-fns';
import { Calendar } from '../../model/Calendar';
import { CalendarService } from '../../service/calendar/calendar.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Ticket } from '../../model/Ticket';
import { TicketService } from '../../service/ticket/ticket.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  standalone: true,
  imports: [DatePipe, JsonPipe, NgClass, NgTemplateOutlet, CommonModule, RouterLink],
})
export class CalendarComponent {
  
  calendar: Calendar = {
    id: 0,
    name: '',
    created: new Date(),
    ownerId: 1
  };
  tickets: Ticket[] = [];

  constructor(private calendarService: CalendarService,
              private route: ActivatedRoute,
              private ticketService: TicketService
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.has('id')) {
      const id = parseInt(this.route.snapshot.paramMap.get('id')!);
      
      this.calendarService.getCalendarById(id).subscribe(
        calendar => { this.calendar = calendar }
      );

      this.ticketService.getTicketsByCalendarId(id).subscribe(
        tickets => { this.tickets = tickets }
      )
    }    
  }  

  deleteTicket(id: number) {
    this.ticketService.deleteTicket(id).subscribe(
      () => window.location.reload()
    )
  }




  public markers = input<CalendarMarkerData[], CalendarMarkerData[] | null>(
    [],
    {
      transform: (value) => value ?? [],
    }
  );
  public markerTpl = input<TemplateRef<{ $implicit: CalendarMarkerData[] }>>();

  public monthChange = output<Date>();

  protected currentDate = signal(startOfToday());
  protected currentMonth = computed(() =>
    format(this.currentDate(), 'MMMM yyyy')
  );

  protected readonly startDateOfSelectedMonth = computed(() => {
    return startOfMonth(this.currentDate());
  });
  protected readonly endDateOfSelectedMonth = computed(() => {
    return endOfMonth(this.currentDate());
  });

  protected readonly days = computed(() => {
    return eachDayOfInterval({
      start: this.startDateOfSelectedMonth(),
      end: this.endDateOfSelectedMonth(),
    });
  });

  readonly #COL_START_CLASSES = [
    '',
    'col-start-2',
    'col-start-3',
    'col-start-4',
    'col-start-5',
    'col-start-6',
    'col-start-7',
  ];

  readonly #markersMap = computed(() => {
    const map: Map<string, CalendarMarkerData[]> = new Map();
    this.markers().forEach((marker) => {
      const date = marker.date;
      const markers = map.get(this.getMarkerMapKey(date)) || [];
      markers.push(marker);
      map.set(this.getMarkerMapKey(date), markers);
    });
    return map;
  });

  protected readonly daysEnriched = computed(() => {
    return this.days().map((day, i) => {
      return {
        day: day,
        isToday: isEqual(day, startOfToday()),
        colStartClass: i === 0 ? this.#COL_START_CLASSES[day.getDay()] : '',
        markers: this.#markersMap().get(this.getMarkerMapKey(day)) || [],
      };
    });
  });

  readonly #dayNames = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  protected readonly dayNamesFormatted = this.#dayNames.map((dayName) => ({
    dayName: dayName,
    isToday: dayName === format(startOfToday(), 'eee'),
  }));

  protected nextMonth() {
    this.currentDate.set(addMonths(this.currentDate(), 1));
    this.monthChange.emit(this.currentDate());
  }

  protected prevMonth() {
    this.currentDate.set(subMonths(this.currentDate(), 1));
    this.monthChange.emit(this.currentDate());
  }

  protected toCurrentMonth(): void {
    this.currentDate.set(startOfToday());
    this.monthChange.emit(this.currentDate());
  }

  protected getMarkerMapKey(date: Date): string {
    return format(date, 'yyyy-MM-dd');
  }
}

export interface CalendarMarkerData<Data = any> {
  date: Date;
  data: Data;
}
