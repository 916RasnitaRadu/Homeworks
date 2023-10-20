import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../model/reservation';
@Injectable({
    providedIn: 'root'
})
export class ReservationService {
    private backendUrl : string = "http://localhost/website/server/";
    private backendUrl2 : string = "http://localhost:5228/Home/";

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
        return this.http.post<Reservation>(`${this.backendUrl2}` + "postReservation",reservation);
    }

    public deleteRequest(reservationId : number) : Observable<any> {
        return this.http.delete<any>(`${this.backendUrl2}` + 'cancelReservation'+"/" + reservationId); 
    }
}