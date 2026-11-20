
package com.example.banque.compte;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class CompteService {

    private final CompteRepository comptes;

    public CompteService(CompteRepository comptes) {
        this.comptes = comptes;
    }

    public List<CompteDto> lister() {
        return comptes.findAll().stream().map(CompteDto::de).toList();
    }

    public CompteDto trouver(Long id) {
        return comptes.findById(id)
                .map(CompteDto::de)
                .orElseThrow(() -> new NoSuchElementException("Compte " + id + " introuvable"));
    }
}
