import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Conversation } from '../interfaces/conversation';
import { ConversationRequest } from '../interfaces/conversation-request';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {
  private basePath: string =  "api/conversations";

  constructor(private http: HttpClient) {}


  public startConversation(request: ConversationRequest): Observable<Conversation> {
    return this.http.post<Conversation>(`${this.basePath}`, request);
  }

  public findConversation(conversationId:number): Observable<Conversation> {
    return this.http.get<Conversation>(`${this.basePath}/${conversationId}`)
  }

  public findAllConversation(): Observable<Conversation[]> {
    return this.http.get<Conversation[]>(`${this.basePath}`)
  }

 
}
