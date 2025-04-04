import { Component } from '@angular/core';
import { LoginFormComponent } from "../../components/login-form/login-form.component";
import { UnauthHeaderComponent } from "../../components/unauth-header/unauth-header.component";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [LoginFormComponent, UnauthHeaderComponent],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss'
})
export class LoginPageComponent {

}
