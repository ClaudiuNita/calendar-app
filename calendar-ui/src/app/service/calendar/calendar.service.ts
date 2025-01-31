import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Calendar } from '../../model/Calendar';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  
  private calendarUrl = '/api/calendar';
  
  constructor(private http: HttpClient) { }
  
  getCalendarById(id: number): Observable<Calendar> {
    return this.http.get<Calendar>(this.calendarUrl + '/' + id);
  }
  
  getAllCalendars(): Observable<Calendar[]> {
    return this.http.get<Calendar[]>(this.calendarUrl);
  }
  
  saveCalendar(calendar: Calendar): Observable<Calendar> {
    return this.http.post<Calendar>(this.calendarUrl, calendar);
  }

  updateCalendar(calendar: Calendar): Observable<Calendar> {
    return this.http.put<Calendar>(this.calendarUrl, calendar);
  }

  deleteCalendar(id: number): Observable<Calendar> {
    return this.http.delete<Calendar>(`${this.calendarUrl}/${id}`);
  }
}
