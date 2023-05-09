import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable , catchError, of} from 'rxjs';
import { Room } from '../model/room';
import { FilterOption } from '../model/filterOptions';
@Injectable({
    providedIn: 'root'
})
export class RoomsService {
    private backendUrl : string = "http://localhost/website/server/";


    constructor(private http : HttpClient) {}

    public fetchPageRooms(capacity: string | null="%", type: string | null ="%", hotel: string | null ="%", page: number | null=1) : Observable<Room[]> {
        return this.http.get<Room[]>(`${this.backendUrl}` + 'viewPage2.php' + 
            '?capacity=' + capacity +
            '&type=' + type +
            '&hotel=' + hotel +
            '&Page=' + page
        ).pipe(catchError(this.handleError<Room[]>('fetchPageRooms',[])));
    }

    public fetchRooms(capacity: string | null="%", type: string | null ="%", hotel: string | null ="%", price: string | null=">", priceValue: number | null=0, page: number | null=1) : Observable<number[]> {
        return this.http.get<number[]>(`${this.backendUrl}` + 'getRoomsID.php')
        .pipe(catchError(this.handleError<number[]>('fetchRooms',[])));
    }

    public fetchFilterOptions() : Observable<FilterOption> {
        return this.http.get<FilterOption>(`${this.backendUrl}` + 'getFilterOptions.php');
    }

    public fetchMaxPage(capacity: string | null="%", type: string | null ="%", hotel: string | null ="%", page: number | null=1) {
        return this.http.get<number>(`${this.backendUrl}` + 'getMaxPage.php' +
        '?capacity=' + capacity +
        '&type=' + type +
        '&hotel=' + hotel +
        '&page=' + page
        ).pipe(catchError(this.handleError<number>('fetchMaxPage',0)));
    }

    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
    
        // TODO: send the error to remote logging infrastructure
        console.error(error); // log to console instead
    
        // Let the app keep running by returning an empty result.
        return of(result as T);
      };
    } 
}