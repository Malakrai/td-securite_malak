
package com.example.banque;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void accesSansAuthentificationRetourne401() throws Exception {
        // Given : utilisateur non authentifié

        // When : il demande la liste des comptes
        // Then : le serveur refuse l'accès (401)
        mockMvc.perform(get("/api/comptes"))
                .andExpect(status().isUnauthorized());
    }
}
