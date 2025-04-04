import { Component, OnInit } from '@angular/core';
import { Ticket } from '../../model/Ticket';
import { TicketService } from '../../service/ticket/ticket.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Datepicker } from 'flowbite';
import type { DatepickerOptions } from 'flowbite';
import type { InstanceOptions } from 'flowbite';

const $datepickerEl: HTMLInputElement = document.getElementById('start') as HTMLInputElement;

const options: DatepickerOptions = {
  defaultDatepickerId: null,
  autohide: false,
  format: 'yyyy/mm/dd',
  maxDate: null,
  minDate: null,
  orientation: 'bottom',
  buttons: false,
  autoSelectToday: 0,
  title: null,
  rangePicker: false,
  onShow: () => {},
  onHide: () => {},
};

const instanceOptions: InstanceOptions = {
  id: 'start',
  override: true
};

const datepicker = new Datepicker($datepickerEl, options, instanceOptions);

@Component({
  selector: 'app-ticket',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.css'
})
export class TicketComponent implements OnInit {
  
  ticket: Ticket = {
    id: 0,
    name: '',
    description: '',
    type: '',
    dateStart: new Date(),
    dateEnd: new Date(),
    created: new Date(),
    ownerId: 1,
    calendarId: 1
  };
  hasId: boolean = false;
  types: String[] = [];
  isCustomType: boolean = false;
  
  constructor(private ticketService: TicketService,
              private router: Router,
              private route: ActivatedRoute
  ) {}
  
  ngOnInit(): void {
    if (this.route.snapshot.paramMap.has('id')) {
      this.hasId = true;
      const id = parseInt(this.route.snapshot.paramMap.get('id')!);
      
      this.ticketService.getTicketById(id).subscribe(
        ticket => this.ticket = ticket 
      )
    }

    if (this.route.snapshot.paramMap.has('calendarId')) {
      this.ticket.calendarId = parseInt(this.route.snapshot.paramMap.get('calendarId')!);
    }

    this.ticketService.getTicketTypesByCalendarId(this.ticket.calendarId).subscribe(
      types => this.types = types.map(type => type.name).slice(2)
    )
  }

  saveTicket(startDate: string, endDate: string) {
    this.ticket.dateStart = new Date(startDate);
    this.ticket.dateEnd = new Date(endDate);

    this.ticketService.saveTicket(this.ticket).subscribe(
      () => this.router.navigateByUrl('/calendar/' + this.ticket.calendarId)
    );
  }

  updateTicket(startDate: string, endDate: string) {
    this.ticket.dateStart = new Date(startDate);
    this.ticket.dateEnd = new Date(endDate);

    this.ticketService.updateTicket(this.ticket).subscribe(
      () => this.router.navigateByUrl('/calendar/' + this.ticket.calendarId)
    );
  }

  toggleCustomType() {
    this.isCustomType = !this.isCustomType;
  }
}
