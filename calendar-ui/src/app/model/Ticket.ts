export interface Ticket {
    id: number;
    name: string;
    description: string;
    type: string;
    dateStart: Date;
    dateEnd: Date;
    created: Date;
    ownerId: number;
    calendarId: number;
}