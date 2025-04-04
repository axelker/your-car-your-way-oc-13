import { Injectable } from '@angular/core';
import { AbstractControl, FormControl } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ControlErrorService {

  constructor() { }

  public buildErrorMessage(fieldName:string,control:FormControl | null | AbstractControl):string {
    if (!control) return '';
    if (!control.invalid || !control.touched) return '';

    if (control.errors?.['required']) {
        return `${fieldName} est requis.`;
    }

    if (control.errors?.['minlength']) {
        const minLength = control.errors['minlength'].requiredLength;
        return `Veuillez saisir au minimum ${minLength} caractères pour le champ ${fieldName}.`;
    }

    if (control.errors?.['maxlength']) {
        const maxLength = control.errors['maxlength'].requiredLength;
        return `Le champ ${fieldName} ne peut pas dépasser ${maxLength} caractères.`;
    }

    if (control.errors?.['email']) {
        return `Veuillez saisir une adresse e-mail valide.`;
    }

    return `Erreur sur le champ ${fieldName}.`;
  }

}
