import { Component, Input } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { SessionService } from '../../../../core/services/session.service';
import { SupportMessageRequest } from '../../interfaces/support-message-request';
import { SupportMessage } from '../../interfaces/support-message';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule,NgFor],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {
  @Input({required:true}) receiverId! : number;
  messages: SupportMessage[] = [];
  newMessage: string = '';
  userInfo! : UserInfo;

  constructor(private chatService: ChatService,private sessionService: SessionService) {
    this.userInfo = this.sessionService.getUser()!;
  }

  ngOnInit(): void {
    this.chatService.listen(`/topic/support/${this.userInfo.id}`).subscribe((msg: SupportMessage) => {
      this.messages.push(msg);
    });
    this.chatService.findAllMessagesByReceiverUserId(this.receiverId).subscribe((v) => this.messages = v);
  }

  send(): void {
    if (!this.newMessage.trim()) return;

    const message : SupportMessageRequest = {
      receiverId: this.receiverId,
      content: this.newMessage,
      senderId: this.userInfo.id
    };

    this.chatService.sendMessage('/app/chat.send', message);
    this.messages.push({ username: this.userInfo.email, content: this.newMessage });
    this.newMessage = '';
  }
}
