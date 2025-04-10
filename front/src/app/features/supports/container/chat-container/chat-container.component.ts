import { Component, signal } from '@angular/core';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { AvaibleUserListComponent } from '../../components/avaible-user-list/avaible-user-list.component';
import { ChatComponent } from '../../components/chat/chat.component';
import { ConversationListComponent } from '../../components/conversation-list/conversation-list.component';
import { Conversation } from '../../interfaces/conversation';
import { ConversationService } from '../../services/conversation.service';
import { AsyncPipe } from '@angular/common';
import { SessionService } from '../../../../core/services/session.service';

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
    private sessionService: SessionService
  ) {
    this.conversationService.findAllConversation().subscribe(this.conversations.set);
    this.currentUserInfo.set(this.sessionService.getUser());
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
