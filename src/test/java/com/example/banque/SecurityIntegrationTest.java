
package com.example.banque;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void accesSansAuthentificationRetourne401() throws Exception {
        // Given 
        var requete = get("/api/comptes");

        // When
        var resultat = mockMvc.perform(requete);

        // Then 
        resultat.andExpect(status().isUnauthorized());
    }

    @Test
    void alicePeutConsulterSonCompte() throws Exception {
        // Given
        var requete = get("/api/comptes/1")
                .with(httpBasic("alice", "alice123"));

        // When 
        var resultat = mockMvc.perform(requete);

        // Then 
        resultat.andExpect(status().isOk());
    }

    @Test
    void aliceNePeutPasConsulterCompteCharles() throws Exception {
        // Given
        var requete = get("/api/comptes/2")
                .with(httpBasic("alice", "alice123"));

        // When 
        var resultat = mockMvc.perform(requete);

        // Then 
        resultat.andExpect(status().isForbidden());
    }

    @Test
    void brunoPeutConsulterCompteDeSonAgence() throws Exception {
        // Given
        var requete = get("/api/comptes/2")
                .with(httpBasic("bruno", "bruno123"));

        // When 
        var resultat = mockMvc.perform(requete);

        // Then 
        resultat.andExpect(status().isOk());
    }

    @Test
    void aliceNePeutPasValiderVirement() throws Exception {
        // Given 
        var requete = post("/api/virements/2/valider")
                .with(httpBasic("alice", "alice123"));

        // When 
        var resultat = mockMvc.perform(requete);

        // Then 
        resultat.andExpect(status().isForbidden());
    }

   
}
