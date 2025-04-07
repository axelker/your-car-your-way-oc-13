import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserInfo } from '../../../core/interfaces/user-info';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SupportService {
    private basePath: string = 'api/support';
    constructor(private http: HttpClient) { }


    public findAvailableUsers(): Observable<UserInfo[]> {
        return this.http.get<UserInfo[]>(`${this.basePath}/users/available`)

    }
}


