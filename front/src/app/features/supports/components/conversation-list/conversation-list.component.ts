import { Component, EventEmitter, Output } from '@angular/core';
import { ConversationService } from '../../services/conversation.service';
import { Conversation } from '../../interfaces/conversation';
import { EMPTY, Observable } from 'rxjs';
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
  conversations: Observable<Conversation[]> = EMPTY;

  constructor(private conversationService: ConversationService){
    this.conversations = this.conversationService.findAllConversation();
  }

  selectConversation(Conversation:Conversation) {
      this.selectedConversation.emit(Conversation);
  }
}
