import { Component } from '@angular/core';
import { SessionService } from '../../../../core/services/session.service';

@Component({
  selector: 'app-home-support-page',
  standalone: true,
  imports: [],
  templateUrl: './home-support-page.component.html',
  styleUrl: './home-support-page.component.scss'
})
export class HomeSupportPageComponent {

  constructor(private sessionService: SessionService) {}

  get username() : string {
    return this.sessionService.getUser()?.email ?? '';
  }
}
