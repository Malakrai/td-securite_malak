/*package com.example.banque.utilisateur.out;

import com.example.banque.utilisateur.Utilisateur;
import com.example.banque.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Faux utilisateur courant : tout le monde est « app.utilisateur-fake » (alice par défaut).
 * Il sert tant que l'appli n'a pas de sécurité. À remplacer par un UtilisateurCourant
 * qui lit le SecurityContext (supprimez alors cette classe).
 *//* 
@Component
public class UtilisateurCourantFake implements UtilisateurCourant {

    private final UtilisateurRepository utilisateurs;
    private final String login;

    public UtilisateurCourantFake(UtilisateurRepository utilisateurs, @Value("${app.utilisateur-fake}") String login) {
        this.utilisateurs = utilisateurs;
        this.login = login;
    }

    @Override
    public Optional<Utilisateur> get() {
        return utilisateurs.findByLogin(login);
    }
}
*/