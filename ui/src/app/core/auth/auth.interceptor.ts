
import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

import { AuthService } from './auth.service';
import { API } from '../api/banque';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  // Ajouter les identifiants uniquement pour notre API
  if (!req.url.startsWith(`${API}/`)) {
    return next(req);
  }

  const authorization = auth.authorization;

  if (authorization) {
    req = req.clone({
      setHeaders: { Authorization: authorization }
    });
  }

  return next(req).pipe(
    catchError((erreur: HttpErrorResponse) => {
      if (erreur.status === 401) {
        auth.deconnecter();
        void router.navigate(['/login']);
      }

      return throwError(() => erreur);
    })
  );
};
