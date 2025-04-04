import { Routes } from '@angular/router';
import { CalendarComponent } from './component/calendar/calendar.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { CalendarFormComponent } from './component/calendar-form/calendar-form.component';
import { TicketComponent } from './component/ticket/ticket.component';

export const routes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'calendar/:id', component: CalendarComponent },
    { path: 'calendar-form', component: CalendarFormComponent },
    { path: 'calendar-form/:id', component: CalendarFormComponent },
    { path: 'ticket/:id', component: TicketComponent },
    { path: 'ticket/calendar/:calendarId', component: TicketComponent },
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
];
