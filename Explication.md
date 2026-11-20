**Auteur : Malak RAI**

## Partie 1 

On a commencé par ajouter Spring Security dans le projet.

On a ensuite créé :
- `SecurityConfig.java` pour configurer la sécurité.
- `BanqueUserDetailsService.java` pour récupérer les utilisateurs depuis la base de données.
- `UtilisateurCourantSecurity.java` pour identifier l'utilisateur connecté.

On a utilisé BCrypt pour les mots de passe et Basic Auth pour l'authentification.

On a aussi activé CORS pour permettre à Angular de communiquer avec Spring Boot.

## Partie 2 

Dans cette partie, on a modifié `AuthService` pour envoyer une requête HTTP GET vers `/api/me` avec `HttpClient`.

On a ensuite créé :
- `auth.interceptor.ts` pour ajouter automatiquement l'en-tête Authorization.
- `auth.guard.ts` pour protéger les pages non accessibles sans connexion.
- Une page `acces-interdit` pour afficher un message d'accès refusé.

On a testé la connexion et la déconnexion avec Alice et Bruno, ainsi que les identifiants incorrects.

## Partie 3 

Dans cette partie, on a commencé à gérer les autorisations selon les rôles.

On a utilisé `hasRole("CONSEILLER")` dans `SecurityConfig.java` pour limiter la validation des virements aux conseillers.

Un utilisateur avec le rôle `CLIENT` ne peut donc pas valider un virement, contrairement à un `CONSEILLER`.

Pour les comptes bancaires, on a conservé le fonctionnement initial de `CompteService.java`. Tous les utilisateurs authentifiés peuvent consulter les comptes.

Le filtrage des comptes par propriétaire ou par agence n'a pas été conservé dans la version finale.

## Tests réalisés

On a utilisé `curl -v` pour tester les requêtes HTTP.

Exemples de commandes :

```powershell
curl.exe -v http://localhost:8080/api/comptes

curl.exe -v -u alice:alice123 http://localhost:8080/api/me

curl.exe -v -u alice:alice123 http://localhost:8080/api/comptes/1

curl.exe -v -u alice:alice123 http://localhost:8080/api/comptes/2

curl.exe -v -u alice:alice123 -X POST http://localhost:8080/api/virements/2/valider

curl.exe -v -u bruno:bruno123 -X POST http://localhost:8080/api/virements/2/valider
```

Les principaux codes HTTP vérifiés sont :
- `200` : requête réussie.
- `401` : utilisateur non authentifié.
- `403` : accès interdit.

On a également créé une classe `SecurityIntegrationTest.java` avec JUnit 5 et MockMvc pour automatiser les tests d'authentification et d'autorisation.
