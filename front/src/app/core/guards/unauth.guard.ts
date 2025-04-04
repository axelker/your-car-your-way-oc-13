import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { SessionService } from "../services/session.service";

export const canActivateUnAuth: CanActivateFn = () => {
  const sessionService: SessionService = inject(SessionService);
  const router = inject(Router);

  if (sessionService.isLogged == false) {
    return true;
  }

  return router.createUrlTree(['/support']);
};