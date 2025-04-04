import { Component } from '@angular/core';
import { BackButtonComponent } from "../../../../shared/components/back-button/back-button.component";

@Component({
  selector: 'app-unauth-header',
  standalone: true,
  imports: [BackButtonComponent],
  templateUrl: './unauth-header.component.html',
  styleUrl: './unauth-header.component.scss'
})
export class UnauthHeaderComponent {

}
