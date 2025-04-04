import { Injectable } from '@angular/core';
import { HttpClient, HttpContext } from '@angular/common/http';
import {  Observable, switchMap, tap } from 'rxjs';
import { RegisterRequest } from '../interfaces/register-request';
import { LoginRequest } from '../interfaces/login-request';
import { UserInfo } from '../interfaces/user-info';
import { UserRequest } from '../interfaces/user-request';
import { SessionService } from './session.service';
import { IGNORED_STATUSES } from '../interceptors/http-error-interceptor';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private basePath: string = '/api/auth';
  constructor(
    private http: HttpClient,
    private sessionService: SessionService,
  ) {}

  public login(request: LoginRequest): Observable<any> {
    return this.http.post(`${this.basePath}/login`, request).pipe(
      switchMap(() => this.getAuthValidation()),
      tap((res: UserInfo) => this.sessionService.logUser(res))
    );
  }

  public logout(): Observable<any> {
    return this.http
      .get(`${this.basePath}/logout`).pipe(
      tap((_) => this.sessionService.logOutUser()));
  }

  public register(request: RegisterRequest): Observable<void> {
    return this.http.post<void>(`${this.basePath}/register`, request);
  }

  public getUserInfo(): Observable<UserInfo> {
    return this.http.get<UserInfo>(`${this.basePath}/me`);
  }

  public updateUserInfo(user: UserRequest): Observable<UserInfo> {
    return this.http.put<UserInfo>(`${this.basePath}/me`, user);
  }

  public checkAuthStatus(): void {
    this.getAuthValidation().subscribe({
      next: (res: UserInfo) => {
        this.sessionService.logUser(res);
      },
      error: () => {
        this.sessionService.logOutUser();
      },
    });
  }

  private getAuthValidation():Observable<UserInfo> {
    return this.http.get<UserInfo>(`${this.basePath}/me`,{context: new HttpContext().set(IGNORED_STATUSES, [401])});
  }
}
