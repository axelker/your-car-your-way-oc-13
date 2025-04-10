import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { MessageRequest } from '../../interfaces/message-request';
import { Conversation } from '../../interfaces/conversation';
import { Message } from '../../interfaces/message';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule,NgFor,NgIf],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit,OnDestroy {
  @Input({required:true}) userInfo! : UserInfo;
  @Input({required:true}) conversation!: Conversation;
  newMessage: string = '';
 
  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.chatService.listen(`/topic/support/${this.conversation.id}`).subscribe((msg: Message) => {
      this.conversation.messages.push(msg);
    });
  }

  ngOnDestroy(): void {
    this.chatService.cleanUp();
  }

  send(): void {
    if (!this.newMessage.trim()) return;

    const message : MessageRequest = {
      conversationId: this.conversation.id,
      content: this.newMessage,
      senderId: this.userInfo.id
    };

    this.chatService.sendMessage('/app/chat.send', message);
    this.newMessage = '';
  }
}
