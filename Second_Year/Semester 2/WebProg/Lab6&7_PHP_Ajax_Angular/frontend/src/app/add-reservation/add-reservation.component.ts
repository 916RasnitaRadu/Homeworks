import { Component, OnInit } from '@angular/core';
import { Reservation } from '../model/reservation';
import { ReservationService } from '../services/reservations.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { map, of } from 'rxjs';

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.css']
})
export class AddReservationComponent implements OnInit {

  public reservationModel = new Reservation(0, 0, this.getTodayDate(), this.getTomorrowDate());
  public addResponse: string = "";
  private reservations: Reservation[] = [];
  private availableRoomsId: number[] = [];
  private reservedIntervals = {};
  currentUrl = "";
  currentId = 0;

  public form = new FormGroup({
    CheckIn: new FormControl(this.getTodayDate(), Validators.required),
    CheckOut: new FormControl(this.getTomorrowDate(), Validators.required)
  });

  constructor(private service: ReservationService, private router: Router, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.queryParams.subscribe(
      params => {
        this.currentId = params['roomId'];
        this.reservationModel.roomID = this.currentId;
        console.log("My room: ", this.currentId);
        this.form.setValue({
          CheckIn: this.getTodayDate(),
          CheckOut: this.getTomorrowDate()
        });
      }
    );
  }

  ngOnInit(): void {
    this.currentUrl = this.router.url;
  }

  public onAdd() {
    this.validate().subscribe(
      (response) => {
        if (response) {
          console.log(this.reservationModel);
          this.service.addRequest(this.reservationModel).subscribe(
            data => {alert("Reservation added successfully!")},
            error => {alert("Add failed!")}
          );
        } else {
          alert("Incorrect dates! There is some overlapping");
        }
      }
    );
  }


  public getTodayDate() {
    const today = new Date();
    const day = today && today.getDate() || -1;
    const dayWithZero = day.toString().length > 1 ? day : '0' + day;
    const month = today && today.getMonth() + 1 || -1;
    const monthWithZero = month.toString().length > 1 ? month : '0' + month;
    const year = today && today.getFullYear() || -1;

    return `${year}-${monthWithZero}-${dayWithZero}`;
  }

  public getTomorrowDate() {
    const today = new Date();
    const tomorrow = new Date(today.setDate(today.getDate() + 1));

    const day = tomorrow && tomorrow.getDate() || -1;
    const dayWithZero = day.toString().length > 1 ? day : '0' + day;
    const month = tomorrow && tomorrow.getMonth() + 1 || -1;
    const monthWithZero = month.toString().length > 1 ? month : '0' + month;
    const year = tomorrow && tomorrow.getFullYear() || -1;

    return `${year}-${monthWithZero}-${dayWithZero}`;
  }

  public isCheckinInInterval(myDate: Date, intervalDateLeft: Date, intervalDateRight: Date) {
    if (intervalDateLeft <= myDate && myDate < intervalDateRight)  // you can check-in when others check-out
      return true;
    return false;
  }

  public isCheckoutInInterval(myDate: Date, intervalDateLeft: Date, intervalDateRight: Date) {
    if (intervalDateLeft < myDate && myDate <= intervalDateRight)  // you can check-out when others check-in
      return true;
    return false;
  }

  public getFormValues() {
    return {
      "CheckIn": this.reservationModel.check_in,
      "CheckOut": this.reservationModel.check_out,
    }
  }

  public validate() {
    let formValues = this.getFormValues();

    let from = new Date(formValues.CheckIn);
    let to = new Date(formValues.CheckOut);
    let roomID = this.currentId;

    if (from >= to) {
      alert("Incorrect dates! Check-out date is before check-in date");
      return of(false);
    }

    let today = new Date(this.getTodayDate());
    if (today > from) {
      alert("You can't reserve dates before today");
      return of(false);
    }

    return this.service.fetchReservationsAndRoomIds().pipe(
      map((array: any) => {
        if (array) {
          this.availableRoomsId = this.getAvailableIds(array);

          if (this.availableRoomsId.includes(roomID)) {
            return true;
          }
          return false;
        } else {
          alert("There aren't any results");
          return false;
        }
      }
      )
    );
  }


  public getAvailableIds(array: any): number[] {
    let formValues = this.getFormValues();

    let from = new Date(formValues.CheckIn);
    let to = new Date(formValues.CheckOut);

    if (array) {
      // 1. check overlapping intervals with with other customers
      let unavailableRoomIds = this.getUnavailableRoomsIds(from, to, array["reservations"]);
       // 2. get only the ids of the available rooms
      let availableRoomIds = this.getAvailableRoomsIds(array["roomIds"], unavailableRoomIds);
      return availableRoomIds;
    }

    return [];
  }

  public getAvailableRoomsIds(allIds: number[], unavailableIds: number[]): number[] {
    let availableRoomIds: number[] = [];

    for (let id of allIds) {
      if (!(unavailableIds.includes(id)) && !(availableRoomIds.includes(id))) {
        availableRoomIds.push(id);
      }
    }

    return availableRoomIds;
  }

  public getUnavailableRoomsIds(from: Date, to: Date, reservations: Reservation[]): number[] {
    let unavailableRoomIds = [];

    for (let r of reservations) {
      let checkin = new Date(r["check_in"]);
      let checkout = new Date(r["check_out"]);
      if (this.isCheckinInInterval(from, checkin, checkout) || this.isCheckoutInInterval(to, checkin, checkout) || this.isCheckinInInterval(checkin, from, to) || this.isCheckoutInInterval(checkout, from, to)) {
        unavailableRoomIds.push(r.roomID);
      }
    }

    return unavailableRoomIds;
  }
}
