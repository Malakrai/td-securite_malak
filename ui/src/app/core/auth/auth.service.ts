import { Injectable, signal } from '@angular/core';
import { API } from '../api/banque';

export interface Utilisateur {
  readonly login: string;
  readonly roles: readonly string[];
}

/**
 * Garde l'utilisateur connecté. Squelette du TD : à compléter en partie 2.
 */
@Injectable({ providedIn: 'root' })
export class AuthService {
  readonly utilisateur = signal<Utilisateur | null>(null);
  private identifiants: string | null = null;

  get authorization(): string | null {
    return this.identifiants;
  }
  /**
   * Vérifie les identifiants auprès du serveur (GET /api/me avec l'en-tête
   * Basic) et mémorise l'utilisateur. Doit échouer (throw) si le serveur répond 401.
   */
  async connecter(login: string, motDePasse: string): Promise<void> {
    // TODO : appeler /api/me, retenir les identifiants pour l'intercepteur,
   const basic = 'Basic ' + btoa(`${login}:${motDePasse}`);

    // When : on vérifie les identifiants auprès du serveur
    const reponse = await fetch(`${API}/me`, {
      headers: {
        Authorization: basic
      }
    });

    // Then : si l'authentification échoue
    if (!reponse.ok) {
      throw new Error('Identifiants incorrects');
    }

    // Sinon, on mémorise l'utilisateur connecté
    const utilisateur: Utilisateur = await reponse.json();

    this.identifiants = basic;
    this.utilisateur.set(utilisateur);
  }

  deconnecter(): void {
    // TODO : oublier les identifiants et l'utilisateur.
    this.identifiants = null;
    this.utilisateur.set(null);
  }
}

