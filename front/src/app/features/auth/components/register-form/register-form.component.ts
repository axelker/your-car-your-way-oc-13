import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ControlErrorService } from '../../../../shared/services/control-error.service';
import { AuthService } from '../../../../core/services/auth.service';
import { RegisterRequest } from '../../../../core/interfaces/register-request';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.scss',
})
export class RegisterFormComponent {
  formGroup!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private controlErrorService: ControlErrorService,
    private authService: AuthService,
    private router: Router,
  ) {
    this.formGroup = this.fb.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern(
          '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$'
        ),
      ]),
    });
  }


  get email() {
    return this.formGroup.get('email');
  }
  get password() {
    return this.formGroup.get('password');
  }


  get emailError() {
    return this.controlErrorService.buildErrorMessage(
      'Adresse e-mail',
      this.email
    );
  }

  get passwordError() {
    return this.controlErrorService.buildErrorMessage(
      'Mot de passe',
      this.password
    );
  }
  onSubmit(): void {
    const request: RegisterRequest = this.formGroup.getRawValue();
    this.authService.register(request).subscribe({
      next: () => this.router.navigate(['/auth/login'])
    });
  }
}
