
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-acces-interdit',
  standalone: true,
  imports: [RouterLink],
  template: `
    <h1>403 - Accès interdit</h1>
    <p>Vous n'avez pas les droits nécessaires pour accéder à cette page.</p>
    <a routerLink="/comptes">Retour aux comptes</a>
  `
})
export class AccesInterdit {}
