import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { NavLinksComponent } from './nav-links/nav-links.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, NgIf, NavLinksComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  isMenuOpen = false;
  constructor(private authService:AuthService,private router: Router) { }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
  closeMenu() : void {
    this.isMenuOpen = false;
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: (_) => {
        this.router.navigate(['/auth']);
      },
    });
  }


}
