
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = (route) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  const utilisateur = auth.utilisateur();

  // Partie 2 : vérifier si l'utilisateur est connecté
  if (!utilisateur) {
    return router.createUrlTree(['/login']);
  }

  // Partie 3 : vérifier si l'utilisateur a le bon rôle
  const rolesAutorises = route.data['roles'] as string[] | undefined;

  if (rolesAutorises && !rolesAutorises.some(
    role => utilisateur.roles.includes(role)
  )) {
    return router.createUrlTree(['/acces-interdit']);
  }

  return true;
};
