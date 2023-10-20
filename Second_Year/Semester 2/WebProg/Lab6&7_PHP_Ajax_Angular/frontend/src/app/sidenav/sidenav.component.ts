import { Component, OnInit } from '@angular/core';
import { navbarData } from './nav-data';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  collapsed = false;
  public navData = navbarData;

  constructor(private router : Router) {}

  ngOnInit(): void {
    
  }

  public goTo(dataLink : string) {
    this.router.navigateByUrl("/" + dataLink);
  }
}
