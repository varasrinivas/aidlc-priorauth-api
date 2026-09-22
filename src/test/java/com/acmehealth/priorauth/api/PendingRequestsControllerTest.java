package com.acmehealth.priorauth.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Starts the whole application in the test and calls the endpoint the way the screen does. */
@SpringBootTest
@AutoConfigureMockMvc
class PendingRequestsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void listsPendingRequestsOldestFirst() throws Exception {
        mvc.perform(get("/requests/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value("PA-10231"))
                .andExpect(jsonPath("$[0].receivedAt").value("2026-10-01"));
    }
}
