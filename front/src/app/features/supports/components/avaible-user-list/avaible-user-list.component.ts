import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { SupportService } from '../../services/support.service';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-avaible-user-list',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './avaible-user-list.component.html',
  styleUrl: './avaible-user-list.component.scss'
})
export class AvaibleUserListComponent implements OnInit {
  @Output() selectedUser: EventEmitter<UserInfo> = new EventEmitter<UserInfo>();
  users: UserInfo[] = [];

  form = new FormGroup({
    userId: new FormControl('')
  });

  constructor(private supportService: SupportService){
    this.supportService.findAvailableUsers().subscribe((v) => this.users = v);
  }
  ngOnInit(): void {
    this.form.get('userId')?.valueChanges.subscribe(userId => {
      const user = this.users.find(u => userId && u.id === +userId);
      if (user) this.selectedUser.emit(user);
    });
  }
}
