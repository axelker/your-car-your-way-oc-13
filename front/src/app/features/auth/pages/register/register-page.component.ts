import { Component } from '@angular/core';
import { RegisterFormComponent } from "../../components/register-form/register-form.component";
import { UnauthHeaderComponent } from "../../components/unauth-header/unauth-header.component";

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [RegisterFormComponent, UnauthHeaderComponent],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.scss'
})
export class RegisterPageComponent {

}
