import { Component, EventEmitter, Output } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { AuthService } from '../../../services/auth.service';
import { EMPTY, Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-nav-links',
  standalone: true,
  imports: [RouterLink,NgIcon,RouterLinkActive,AsyncPipe],
  templateUrl: './nav-links.component.html',
  styleUrl: './nav-links.component.scss'
})
export class NavLinksComponent {
  @Output() logout: EventEmitter<void> = new EventEmitter<void>();

  showContact: Observable<boolean> = EMPTY;

  constructor(private authService:AuthService){
    this.showContact = this.authService.isClientLogged();
  }


  
}
