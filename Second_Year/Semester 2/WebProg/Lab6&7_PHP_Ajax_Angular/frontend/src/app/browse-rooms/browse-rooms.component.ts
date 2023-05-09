import { Component, OnInit } from '@angular/core';
import { FilterOption } from '../model/filterOptions';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Room } from '../model/room';
import { RoomsService } from '../services/rooms.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-browse-rooms',
  templateUrl: './browse-rooms.component.html',
  styleUrls: ['./browse-rooms.component.css']
})
export class BrowseRoomsComponent implements OnInit{

  columns: string[] = ["ID", "Capacity", "Type", "Hotel", "Price", "Reserve"]; // all the columns of the table
  filterColumns : string[] = ["capacity", "type", "hotel"];  // the columns needed for filtering
  filterOptions : FilterOption = {capacity: [], type: [], hotel: [], price: []} // all available unique values for each column, to filter result

  form = new FormGroup({
    capacity : new FormControl('%', Validators.required),
    type : new FormControl('%', Validators.required),
    hotel : new FormControl('%', Validators.required),
  });

  rooms : Room[] = [];
  currentPage : number = 1;
  maxPage: number = 0;

  constructor(private service: RoomsService, private router: Router) { }

  ngOnInit(): void {
    this.getFilteringOptions();
    this.getRooms();
    this.getMaxPage();
  }


  // Getters

  public getFormValues() {
    return {
      "capacity": this.form.value.capacity, 
      "type": this.form.value.type, 
      "hotel": this.form.value.hotel,
      "page": this.currentPage
    }
    }
  

  public getMaxPage() {
    let formValues = Object.values(this.getFormValues());
    this.service.fetchMaxPage(this.form.value.capacity,this.form.value.type,this.form.value.hotel,this.currentPage).subscribe(
      page => {
        this.maxPage = page;
        console.log("Here ", page);
      }
    );
  }

  public getRooms(capacity: string | null="%", type: string | null ="%", hotel: string | null ="%", page: number | null=1) : void {
    let formValues = Object.values(this.getFormValues());
    this.service.fetchPageRooms(this.form.value.capacity,this.form.value.type,this.form.value.hotel,this.currentPage).subscribe(
      rooms => {
        if (rooms) {
          console.log(rooms);
          this.rooms = rooms;
        }
        else {
          alert("There aren't any results!");
        }
      }
    );
  }

  public getFilteringOptions() : void {
    this.service.fetchFilterOptions().subscribe(
      options => {
        console.log(options);
        this.filterOptions = options;
      }
    );
  }

  // Validators

  public validateAndFixPage() : number {
    if (this.currentPage > this.maxPage) {
      alert("There aren't any results!");
      return -1;
    }

    if (this.currentPage <= 0) {
      alert("There aren't any results!");
      return 1;
    }

    return 0;
  }


  // buttons

  public submit() {
    this.currentPage = 1;
    let formValues = Object.values(this.getFormValues());

    this.getRooms(this.form.value.capacity,this.form.value.type,this.form.value.hotel,this.currentPage);
    this.getMaxPage();

  }

  public next(){
    this.currentPage += 1;
    this.updateRoomsPage();
  }

  public prev(){
    this.currentPage -= 1;
    this.updateRoomsPage();
  }

  public updateRoomsPage() {
    let formValues = Object.values(this.getFormValues());
    let value = this.validateAndFixPage();
    //this.getRooms(this.form.value.capacity,this.form.value.type,this.form.value.hotel,this.currentPage);
    if (value === 0) {
      this.getRooms(this.form.value.capacity,this.form.value.type,this.form.value.hotel,this.currentPage);
    } else {
      this.currentPage += value;
    }
  }

  public reserveRoom(id : any) : void{
    this.router.navigateByUrl('/add-reservation?roomId='+id);
  }
}

