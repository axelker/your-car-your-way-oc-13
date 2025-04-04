import { Component } from '@angular/core';
import { Observable, EMPTY } from 'rxjs';
import { AuthService } from '../../../../core/services/auth.service';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-chat-list',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss'
})
export class ChatListComponent {

  showNew: Observable<boolean> = EMPTY;
  
  constructor(private authService:AuthService){
      this.showNew = this.authService.isClientLogged();
  }

}
