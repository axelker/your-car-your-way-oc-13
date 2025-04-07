import { Component, EventEmitter, Output } from '@angular/core';
import { SupportService } from '../../services/support.service';
import { EMPTY, Observable } from 'rxjs';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-avaible-user-list',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './avaible-user-list.component.html',
  styleUrl: './avaible-user-list.component.scss'
})
export class AvaibleUserListComponent {
  @Output() selectedUser: EventEmitter<UserInfo> = new EventEmitter<UserInfo>();
  users:Observable<UserInfo[]> = EMPTY;
  constructor(private supportService: SupportService){
    this.users = this.supportService.findAvailableUsers();
  }

  selectUser(user:UserInfo) {
    this.selectedUser.emit(user);
  }
}
