import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../model/reservation';
@Injectable({
    providedIn: 'root'
})
export class ReservationService {
    private backendUrl : string = "http://localhost/website/server/";

    constructor(private http : HttpClient) {}

    public fetchReservations() : Observable<Reservation[]> {
        return this.http.get<Reservation[]>(`${this.backendUrl}` + 'getReservations.php');
    }

    public fetchReservationsId() : Observable<string[]> {
        return this.http.get<string[]>(`${this.backendUrl}` + 'getReservationsID.php');
    }

    public fetchReservationsAndRoomIds() : Observable<string[]> {
        return this.http.get<string[]>(`${this.backendUrl}` + 'getReservationsAndRoomIds.php');
    }

    public addRequest(reservation : Reservation) : Observable<any> {
        return this.http.post<any>(`${this.backendUrl}` + 'postReservation2.php',reservation);
    }

    public deleteRequest(reservationId : number) : Observable<any> {
        return this.http.post<any>(`${this.backendUrl}` + 'deleteReservation2.php', reservationId); 
    }
}