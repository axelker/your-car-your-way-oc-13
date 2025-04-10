import { Injectable } from '@angular/core';
import { UserInfo } from '../interfaces/user-info';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private userSubject: BehaviorSubject<UserInfo | null> = new BehaviorSubject<UserInfo | null>(null);

  constructor() { }

  public getUser() : UserInfo | null {
    return this.userSubject.getValue();
  }
  get isLogged(): boolean {
    return this.getUser() !== null;
  }
  public logUser(user:UserInfo):void {
    this.userSubject.next(user);
  }

  public logOutUser():void {
    this.userSubject.next(null);
  }
}
