import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BodyComponent } from './body/body.component';
import { BrowseRoomsComponent } from './browse-rooms/browse-rooms.component';
import { BrowseReservationsComponent } from './browse-reservations/browse-reservations.component';
import { AddReservationComponent } from './add-reservation/add-reservation.component';

const routes: Routes = [
  {path: '', component: BodyComponent},
  {path: 'browse-rooms', component: BrowseRoomsComponent},
  {path: 'browse-reservations', component: BrowseReservationsComponent},
  {path: 'add-reservation', component: AddReservationComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
