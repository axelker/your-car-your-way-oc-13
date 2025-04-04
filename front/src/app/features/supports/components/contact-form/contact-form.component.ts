import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SupportRequest } from '../../interfaces/support-request';
import { ControlErrorService } from '../../../../shared/services/control-error.service';
import { SupportRequestService } from '../../services/support-request.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss'
})
export class ContactFormComponent {
  formGroup!: FormGroup;

    constructor(
      private fb: FormBuilder,
      private controlErrorService: ControlErrorService,
      private supportRequestService: SupportRequestService,
      private toastr: ToastrService
    ) {
    this.formGroup = this.fb.group({
      subject: new FormControl('', [Validators.required]),
      content: new FormControl('', [
        Validators.required,
      ]),
    });
  }
  get subject() {
    return this.formGroup.get('subject');
  }

  get content() {
    return this.formGroup.get('content');
  }

  get subjectError() {
    return this.controlErrorService.buildErrorMessage("Sujet",this.subject);
  }

  get contentError() {
    return this.controlErrorService.buildErrorMessage("Message",this.content);
  }
  onSubmit(): void {
      const request: SupportRequest = this.formGroup.getRawValue();
      request.sentAt = new Date();
      this.supportRequestService.sendSupportRequest(request).subscribe({
        next: (_) => {
          this.toastr.success("Requête envoyée avec succès !");
        },
      });
  }
}
