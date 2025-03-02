import { Injectable } from '@angular/core';
import { Ticket } from '../../model/Ticket';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  
  private ticketUrl = '/api/ticket';
  
  constructor(private http: HttpClient) { }
  
  getTicketById(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(this.ticketUrl + '/' + id);
  }
  
  getTicketsByCalendarId(id: number): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.ticketUrl + "/calendar/" + id)
  }

  getTicketTypesByCalendarId(id: number): Observable<any[]> {
    return this.http.get<any[]>(this.ticketUrl + "/type/calendar/" + id)
  }

  saveTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.post<Ticket>(this.ticketUrl, ticket);
  }

  updateTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(this.ticketUrl, ticket);
  }

  deleteTicket(id: number): Observable<Ticket> {
    return this.http.delete<Ticket>(this.ticketUrl + '/' + id);
  }
}
