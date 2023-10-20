import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "../model/user";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private backendUrl : string = "http://localhost:5228";

    constructor(private http : HttpClient) {}

    public sendCredentials(user : User) : Observable<User>{
        return this.http.post<User>(`${this.backendUrl}` + "/Home", user);
    }
}