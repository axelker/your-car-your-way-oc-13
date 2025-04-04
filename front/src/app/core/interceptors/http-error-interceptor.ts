import { inject } from '@angular/core';
import { HttpEvent, HttpRequest, HttpErrorResponse, HttpHandlerFn, HttpContextToken } from '@angular/common/http';
import { EMPTY, Observable, catchError, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';


export const IGNORED_STATUSES = new HttpContextToken<number[]>(() => []);


export function httpErrorInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
    const toastr = inject(ToastrService);
    const ignoredStatuses = req.context.get(IGNORED_STATUSES);
    return next(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (ignoredStatuses?.includes(error.status)) {
          return throwError(() => error);
        }
        handleError(error,toastr);
        return throwError(() => error);
      })
    );
  }

function handleError(error: HttpErrorResponse,toastr:ToastrService): void {
    if (error.status === 0) {
      toastr.error('Impossible de contacter le serveur. Vérifiez votre connexion.', 'Erreur Réseau');
    } else if (error.status >= 400 && error.status < 500) {
      toastr.error(error.error?.message || 'Une erreur s’est produite.', `Erreur ${error.status}`);
    } else if (error.status >= 500) {
      toastr.error('Erreur interne du serveur. Veuillez réessayer plus tard.', 'Erreur Serveur');
    } else {
      toastr.error('Une erreur inconnue s’est produite.', 'Erreur');
    }
}

