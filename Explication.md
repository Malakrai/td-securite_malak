Author: Malak RAI

## Partie 1

On a commencé par ajouter Spring Security dans le projet.

On a ensuite créé :
- `SecurityConfig.java` pour configurer la sécurité.
- `BanqueUserDetailsService.java` pour récupérer les utilisateurs depuis la base de données.
- `UtilisateurCourantSecurity.java` pour identifier l'utilisateur connecté.

On a utilisé BCrypt pour les mots de passe et Basic Auth pour l'authentification.

On a aussi activé CORS pour autoriser les requêtes Angular vers Spring Boot.

## Partie 2 

Dans cette partie, on a modifié `AuthService` pour envoyer une requête HTTP GET vers `/api/me` avec `HttpClient`.

On a ensuite créé :
- `auth.interceptor.ts` pour ajouter automatiquement l'en-tête Authorization.
- `auth.guard.ts` pour protéger les pages.
- Une page `acces-interdit` pour les utilisateurs qui n'ont pas les droits nécessaires.

On a testé la connexion et la déconnexion avec Alice et Bruno.

## Partie 3 

On a ajouté des restrictions selon les rôles :

- CLIENT : peut consulter uniquement ses propres comptes.
- CONSEILLER : peut consulter les comptes de son agence et valider les virements.

On a modifié `CompteService.java` pour vérifier les autorisations avant de retourner les informations d'un compte.

On a également utilisé `hasRole("CONSEILLER")` dans Spring Security pour protéger la validation des virements.

## Tests réalisés

On a utilisé curl avec l'option `-v` pour vérifier les réponses HTTP.

Exemples de commandes :

```powershell
curl.exe -v http://localhost:8080/api/comptes

curl.exe -v -u alice:alice123 http://localhost:8080/api/me

curl.exe -v -u alice:alice123 http://localhost:8080/api/comptes/1

curl.exe -v -u alice:alice123 http://localhost:8080/api/comptes/2

curl.exe -v -u bruno:bruno123 http://localhost:8080/api/comptes
```

Résultats obtenus :
- 200 : accès autorisé.
- 401 : utilisateur non authentifié.
- 403 : accès interdit.

On a aussi créé une classe `SecurityIntegrationTest.java` avec JUnit 5 et MockMvc pour automatiser les tests de sécurité.
