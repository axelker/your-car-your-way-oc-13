import { Component, Input } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { AuthService } from '../../../../core/services/auth.service';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { SessionService } from '../../../../core/services/session.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [FormsModule,NgFor],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {
  @Input({required:true}) receiverId! : number;
  messages: { sender: string, content: string }[] = []; //Todo: init with chat service (findAllByUserId)
  newMessage: string = '';
  userInfo! : UserInfo;

  constructor(private chatService: ChatService,private sessionService: SessionService) {
    this.userInfo = this.sessionService.getUser()!;
  }

  ngOnInit(): void {
    this.chatService.listen(`/topic/support/${this.userInfo.id}`).subscribe((msg: any) => {
      console.log(msg)
      this.messages.push(msg);
    });
  }

  send(): void {
    if (!this.newMessage.trim()) return;

    const message = {
      receiverId: this.receiverId,
      content: this.newMessage,
      senderId: this.userInfo.id
    };

    this.chatService.sendMessage('/app/chat.send', message);
    this.messages.push({ sender: this.userInfo.email, content: this.newMessage });
    this.newMessage = '';
  }
}
