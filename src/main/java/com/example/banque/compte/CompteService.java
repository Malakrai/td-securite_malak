
package com.example.banque.compte;

import com.example.banque.utilisateur.ConseillerRepository;
import com.example.banque.utilisateur.Role;
import com.example.banque.utilisateur.Utilisateur;
import com.example.banque.utilisateur.out.UtilisateurCourant;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class CompteService {

    private final CompteRepository comptes;
    private final UtilisateurCourant utilisateurCourant;
    private final ConseillerRepository conseillers;

    public CompteService(
            CompteRepository comptes,
            UtilisateurCourant utilisateurCourant,
            ConseillerRepository conseillers) {
        this.comptes = comptes;
        this.utilisateurCourant = utilisateurCourant;
        this.conseillers = conseillers;
    }

    public List<CompteDto> lister() {
        Utilisateur utilisateur = utilisateurCourant.get()
                .orElseThrow(() -> new AccessDeniedException("Accès refusé"));

        return comptes.findAll().stream()
                .filter(compte -> {
                    // Un client voit uniquement ses propres comptes
                    if (utilisateur.getRole() == Role.CLIENT) {
                        return compte.getTitulaire().getUtilisateur()
                                .getId().equals(utilisateur.getId());
                    }

                    // Un conseiller voit les comptes de son agence
                    if (utilisateur.getRole() == Role.CONSEILLER) {
                        return conseillers.findByUtilisateurLogin(utilisateur.getLogin())
                                .map(conseiller -> conseiller.getAgence().getId()
                                        .equals(compte.getAgence().getId()))
                                .orElse(false);
                    }

                    return false;
                })
                .map(CompteDto::de)
                .toList();
    }

    public CompteDto trouver(Long id) {
        Utilisateur utilisateur = utilisateurCourant.get()
                .orElseThrow(() -> new AccessDeniedException("Accès refusé"));

        Compte compte = comptes.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Compte " + id + " introuvable"));

        boolean autorise = false;

        // Un client peut consulter uniquement ses propres comptes
        if (utilisateur.getRole() == Role.CLIENT) {
            autorise = compte.getTitulaire().getUtilisateur()
                    .getId().equals(utilisateur.getId());
        }

        // Un conseiller peut consulter les comptes de son agence
        if (utilisateur.getRole() == Role.CONSEILLER) {
            autorise = conseillers.findByUtilisateurLogin(utilisateur.getLogin())
                    .map(conseiller -> conseiller.getAgence().getId()
                            .equals(compte.getAgence().getId()))
                    .orElse(false);
        }

        if (!autorise) {
            throw new AccessDeniedException("Accès interdit à ce compte");
        }

        return CompteDto.de(compte);
    }
}
