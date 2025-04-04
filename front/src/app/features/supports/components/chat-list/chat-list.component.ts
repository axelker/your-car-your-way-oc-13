import { Component } from '@angular/core';
import { SessionService } from '../../../../core/services/session.service';
import { roles } from '../../../../core/enums/roles.enum';

@Component({
  selector: 'app-chat-list',
  standalone: true,
  imports: [],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss'
})
export class ChatListComponent {

  constructor(private sessionService: SessionService) {

  }

  public showNew():boolean {
    return !!this.sessionService.getUser() && this.sessionService.getUser()?.role == roles.CLIENT;
  }
}
