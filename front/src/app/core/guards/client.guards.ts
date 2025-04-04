import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { map } from 'rxjs';

export const canActivateClient: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isClientLogged().pipe(
    map(value => value === true ? true : router.createUrlTree(['/']))
  );

};
