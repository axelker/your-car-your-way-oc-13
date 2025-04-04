import { Component, Input } from '@angular/core';
import { BackButtonComponent } from "../back-button/back-button.component";

@Component({
  selector: 'app-page-title-header',
  standalone: true,
  imports: [BackButtonComponent],
  templateUrl: './page-title-header.component.html',
  styleUrl: './page-title-header.component.scss'
})
export class PageTitleHeaderComponent {
  @Input() title: string = '';

}
