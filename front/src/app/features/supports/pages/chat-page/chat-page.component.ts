import { Component } from '@angular/core';
import { ChatComponent } from '../../components/chat/chat.component';
import { AvaibleUserListComponent } from '../../components/avaible-user-list/avaible-user-list.component';
import { Conversation } from '../../interfaces/conversation';
import { ConversationListComponent } from '../../components/conversation-list/conversation-list.component';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { ConversationService } from '../../services/conversation.service';

@Component({
  selector: 'app-chat-page',
  standalone: true,
  imports: [ConversationListComponent,AvaibleUserListComponent,ChatComponent],
  templateUrl: './chat-page.component.html',
  styleUrl: './chat-page.component.scss'
})
export class ChatPageComponent {
  conversationSelected: Conversation | null = null;

  //TODO: it's dumb component extrac logic part inside a component.
  constructor(private conversationService: ConversationService) {}
  selectUser(user:UserInfo) {
    this.conversationService.startConversation({participantId: user.id}).subscribe((v) => this.conversationSelected = v);
  }

  selectConversation(conversation:Conversation) {
    this.conversationSelected = conversation;
  }

  closeChat() {
    this.conversationSelected = null;
  }

}
