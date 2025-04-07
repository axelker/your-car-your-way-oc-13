import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { RxStomp } from '@stomp/rx-stomp';
import { rxStompConfig } from '../config/rx-stomp.config';
import { HttpClient } from '@angular/common/http';
import { SupportMessage } from '../interfaces/support-message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private rxStomp: RxStomp;

  constructor(private http: HttpClient) {
    this.rxStomp = new RxStomp();
    this.rxStomp.configure(rxStompConfig);
    this.rxStomp.activate();
  }

  public sendMessage(destination: string, body: any): void {
    this.rxStomp.publish({
      destination,
      body: JSON.stringify(body)
    });
  }

  public listen(destination: string) {
    return this.rxStomp.watch(destination).pipe(
      map(message => JSON.parse(message.body))
    );
  }

  public findAllMessagesByReceiverUserId(receiverId:number): Observable<SupportMessage[]> {
    return this.http.get<SupportMessage[]>(`api/support/messages/${receiverId}`)
  }

 
}
