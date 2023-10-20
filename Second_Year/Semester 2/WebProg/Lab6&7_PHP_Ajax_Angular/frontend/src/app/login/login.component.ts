import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { User } from '../model/user';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private username : string = "";
  private password : string = "";

  constructor(private router : Router, private userService : UserService) {}

  public verifyCredentials(loginForm : NgForm) : void {
    this.userService.sendCredentials(loginForm.value).subscribe(
      (response : User) => {
        console.log(response);
        if (response === null) {
          alert("Wrong username or password!");
        }
        else this.router.navigateByUrl("/body");
      },
      (error : HttpErrorResponse) => {
        alert("Wrong username or password!");
      }
    );
  }
}
