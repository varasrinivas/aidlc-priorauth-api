package com.acmehealth.priorauth.api;

import com.acmehealth.priorauth.store.RequestStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** GET /requests/pending: the list the reviewer screen shows, oldest first. */
@RestController
public class PendingRequestsController {

    private final RequestStore store;

    public PendingRequestsController(RequestStore store) {
        this.store = store;
    }

    @GetMapping("/requests/pending")
    public List<PendingRequestResponse> pending() {
        return store.pending().stream().map(PendingRequestResponse::from).toList();
    }
}
