import { Injectable } from '@angular/core';
import { UserInfo } from '../interfaces/user-info';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private USER_SESSION_KEY: string = "user";

  constructor() { }

  public getUser() : UserInfo | null {
    const user : string | null = localStorage.getItem(this.USER_SESSION_KEY);
    return user === null ? null : JSON.parse(user);
  }
  get isLogged(): boolean {
    return this.getUser() !== null;
  }
  public logUser(user:UserInfo):void {
    localStorage.setItem(this.USER_SESSION_KEY,JSON.stringify(user));
  }

  public logOutUser():void {
    localStorage.removeItem(this.USER_SESSION_KEY);
  }
}
