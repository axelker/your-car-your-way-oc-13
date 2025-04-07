import { Component } from '@angular/core';
import { ChatComponent } from '../../components/chat/chat.component';
import { AvaibleUserListComponent } from '../../components/avaible-user-list/avaible-user-list.component';
import { UserInfo } from '../../../../core/interfaces/user-info';

@Component({
  selector: 'app-chat-page',
  standalone: true,
  imports: [ChatComponent,AvaibleUserListComponent],
  templateUrl: './chat-page.component.html',
  styleUrl: './chat-page.component.scss'
})
export class ChatPageComponent {
  userSelectedToChat: UserInfo | null = null;


  selectUser(user:UserInfo) {
    this.userSelectedToChat = user;
    console.log(this.userSelectedToChat)
  }
  closeChat() {
    this.userSelectedToChat = null;
  }

}
