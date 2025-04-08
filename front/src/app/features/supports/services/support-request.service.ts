import { Injectable } from '@angular/core';
import { SupportRequest } from '../interfaces/support-request';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SupportRequestService {
  private basePath: string = '/api/supports/request';

  constructor(private http: HttpClient) { }

  public sendSupportRequest(request:SupportRequest): Observable<void> {
   return this.http.post<void>(`${this.basePath}`,request);
  }


}
