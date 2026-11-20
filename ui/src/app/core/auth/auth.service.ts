
import { Injectable, inject, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
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
  private readonly http = inject(HttpClient);

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
    // puis renseigner `utilisateur`.

    const basic = 'Basic ' + btoa(`${login}:${motDePasse}`);

    const utilisateur = await firstValueFrom(
      this.http.get<Utilisateur>(`${API}/me`, {
        headers: new HttpHeaders({
          Authorization: basic
        })
      })
    );

    this.identifiants = basic;
    this.utilisateur.set(utilisateur);
  }

  deconnecter(): void {
    // TODO : oublier les identifiants et l'utilisateur.
    this.identifiants = null;
    this.utilisateur.set(null);
  }
}
