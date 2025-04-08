import { Component, Input } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { SessionService } from '../../../../core/services/session.service';
import { MessageRequest } from '../../interfaces/message-request';
import { Conversation } from '../../interfaces/conversation';
import { Message } from '../../interfaces/message';
import { ConversationService } from '../../services/conversation.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule,NgFor,NgIf],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {
  @Input({required:true}) conversationId! : number;
  conversation!: Conversation;
  newMessage: string = '';
  userInfo! : UserInfo;

  constructor(private chatService: ChatService,
    private conversationService: ConversationService,
    private sessionService: SessionService) {
    this.userInfo = this.sessionService.getUser()!;
  }

  ngOnInit(): void {
    this.chatService.listen(`/topic/support/${this.conversationId}`).subscribe((msg: Message) => {
      this.conversation.messages.push(msg);
    });
    this.conversationService.findConversation(this.conversationId).subscribe((c) => this.conversation = c);
  }

  send(): void {
    if (!this.newMessage.trim()) return;

    const message : MessageRequest = {
      conversationId: this.conversationId,
      content: this.newMessage,
      senderId: this.userInfo.id
    };

    this.chatService.sendMessage('/app/chat.send', message);
    this.newMessage = '';
  }
}
