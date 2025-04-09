import { Component, signal } from '@angular/core';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { AvaibleUserListComponent } from '../../components/avaible-user-list/avaible-user-list.component';
import { ChatComponent } from '../../components/chat/chat.component';
import { ConversationListComponent } from '../../components/conversation-list/conversation-list.component';
import { Conversation } from '../../interfaces/conversation';
import { ConversationService } from '../../services/conversation.service';
import { AuthService } from '../../../../core/services/auth.service';
import { Observable, EMPTY } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-chat-container',
  standalone: true,
  imports: [ConversationListComponent,AvaibleUserListComponent,ChatComponent,AsyncPipe],
  templateUrl: './chat-container.component.html',
  styleUrl: './chat-container.component.scss'
})
export class ChatContainerComponent {
  conversations = signal<Conversation[]>([]);
  currentUserInfo = signal<UserInfo | null>(null);
  conversationSelected = signal<Conversation | null>(null);

  constructor(
    private conversationService: ConversationService,
    private authService: AuthService
  ) {
    this.conversationService.findAllConversation().subscribe(this.conversations.set);
    this.authService.getUserInfo().subscribe(this.currentUserInfo.set);
  }

  selectUser(user: UserInfo) {
    this.conversationService
      .startConversation({ participantId: user.id })
      .subscribe((v) => this.conversationSelected.set(v));
  }

  selectConversation(conversation: Conversation) {
    this.conversationSelected.set(conversation);
  }

  closeChat() {
    this.conversationSelected.set(null);
  }
}
