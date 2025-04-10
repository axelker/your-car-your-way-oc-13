import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Conversation } from '../../interfaces/conversation';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-conversation-list',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './conversation-list.component.html',
  styleUrl: './conversation-list.component.scss'
})
export class ConversationListComponent {
  @Output() selectedConversation: EventEmitter<Conversation> = new EventEmitter<Conversation>();
  @Input() conversations:Conversation[] = [];

  constructor(){
  }

  selectConversation(conversation:Conversation) {
      this.selectedConversation.emit(conversation);
  }
}
