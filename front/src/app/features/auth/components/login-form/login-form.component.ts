import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ControlErrorService } from '../../../../shared/services/control-error.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';
import { LoginRequest } from '../../../../core/interfaces/login-request';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent {
 formGroup!: FormGroup;

    constructor(
      private fb: FormBuilder,
      private controlErrorService: ControlErrorService,
      private authService: AuthService,
      private router: Router,
    ) {
    this.formGroup = this.fb.group({
      identifier: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
      ]),
    });
  }
  get identifier() {
    return this.formGroup.get('identifier');
  }

  get password() {
    return this.formGroup.get('password');
  }

  get identifierError() {
    return this.controlErrorService.buildErrorMessage("Identifiant",this.identifier);
  }

  get passwordError() {
    return this.controlErrorService.buildErrorMessage("Mot de passe",this.password);
  }
  onSubmit(): void {
      const request: LoginRequest = this.formGroup.getRawValue();
      this.authService.login(request).subscribe({
        next: (_) => {
          this.router.navigate(['/articles']);
        },
      });
  }
}
