import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { RxStomp } from '@stomp/rx-stomp';
import { rxStompConfig } from '../config/rx-stomp.config';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private rxStomp: RxStomp;

  constructor() {
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
 
}
