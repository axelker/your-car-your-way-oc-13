import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { roles } from '../enums/roles.enum';

export const canActivateClient: CanActivateFn = () => {
  const sessionService = inject(SessionService);
  const router = inject(Router);

  if (sessionService.getUser() != null && sessionService.getUser()?.role == roles.CLIENT) {
    return true;
  }

  return router.createUrlTree(['/']);
};
