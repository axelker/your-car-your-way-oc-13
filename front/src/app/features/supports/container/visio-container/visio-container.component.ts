import { Component, signal, ViewChild } from '@angular/core';
import { UserInfo } from '../../../../core/interfaces/user-info';
import { SessionService } from '../../../../core/services/session.service';
import { AvaibleUserListComponent } from '../../components/avaible-user-list/avaible-user-list.component';
import { VisioComponent } from '../../components/visio/visio.component';

@Component({
  selector: 'app-visio-container',
  standalone: true,
  imports: [AvaibleUserListComponent,VisioComponent],
  templateUrl: './visio-container.component.html',
  styleUrl: './visio-container.component.scss'
})
export class VisioContainerComponent {
  @ViewChild('avaibleUser')
  avaibaleUserList!: AvaibleUserListComponent;
  @ViewChild('visio')
  visio!: VisioComponent;
  currentUserInfo = signal<UserInfo | null>(null);
  userSelected = signal<UserInfo | null>(null);

  constructor(
    private sessionService: SessionService
  ) {
    this.currentUserInfo.set(this.sessionService.getUser());
  }

  selectUser(user: UserInfo) {
    this.userSelected.set(user);
  }


  closeVisio() {
    this.visio.exitCall();
    this.userSelected.set(null);
    this.avaibaleUserList.form.patchValue({userId:''});
  }
}
