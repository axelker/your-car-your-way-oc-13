import { Component } from '@angular/core';
import { NgIcon } from '@ng-icons/core';
@Component({
  selector: 'app-back-button',
  standalone: true,
  imports: [NgIcon],
  templateUrl: './back-button.component.html',
  providers: [],
  styleUrl: './back-button.component.scss'
})
export class BackButtonComponent {
  back() {
    window.history.back();
  }
}
