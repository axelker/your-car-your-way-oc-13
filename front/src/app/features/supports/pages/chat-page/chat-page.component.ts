import { Component } from '@angular/core';
import { ChatContainerComponent } from '../../container/chat-container/chat-container.component';

@Component({
  selector: 'app-chat-page',
  standalone: true,
  imports: [ChatContainerComponent],
  templateUrl: './chat-page.component.html',
  styleUrl: './chat-page.component.scss'
})
export class ChatPageComponent {

}
