import { Component, OnInit } from '@angular/core';
import { Reservation } from '../model/reservation';
import { ReservationService } from '../services/reservations.service';
import { REMOVE_STYLES_ON_COMPONENT_DESTROY } from '@angular/platform-browser';


@Component({
  selector: 'app-browse-reservations',
  templateUrl: './browse-reservations.component.html',
  styleUrls: ['./browse-reservations.component.css']
})
export class BrowseReservationsComponent implements OnInit {
  columns: string[] = ["ID", "RoomID", "CheckIn", "CheckOut"," "];  // all columns of the table
  reservations: Reservation[] = [];

  constructor(private service : ReservationService) { }

  ngOnInit(): void {
    this.getReservations();
  }

  public getReservations() : void {
    this.service.fetchReservations().subscribe(
      result => {
        if (result) {
          console.log(result);
          this.reservations = result;
        } else {
          alert("There aren't any results!");
        }
      }
    );
  }

  public cancelReservation(id : any) : void {
    this.service.deleteRequest(id).subscribe(
      response => {
      //  alert(response);
        this.getReservations();
      }
    );
    
  }
}
