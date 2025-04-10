import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { RxStomp } from '@stomp/rx-stomp';
import { rxStompConfig } from '../config/rx-stomp.config';
import { Message } from '../interfaces/message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private rxStomp: RxStomp = new RxStomp();

  constructor() {
    this.rxStomp.configure(rxStompConfig);
  }

  init() {
    this.rxStomp.activate();
  }

  cleanUp() : void {
    if (this.rxStomp.connected()) {
      this.rxStomp.deactivate();
    }
  }

  public sendMessage(destination: string, body: any): void {
    this.rxStomp.publish({
      destination,
      body: JSON.stringify(body)
    });
  }

  public listen(destination: string): Observable<Message> {
    return this.rxStomp.watch(destination).pipe(
      map(message => JSON.parse(message.body))
    );
  }
 
}
