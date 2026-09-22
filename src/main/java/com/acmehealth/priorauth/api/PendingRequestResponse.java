package com.acmehealth.priorauth.api;

import com.acmehealth.priorauth.model.PriorAuthRequest;

import java.time.LocalDate;

/** One row of the pending list as the reviewer screen receives it (JSON). */
public record PendingRequestResponse(
        String id,
        String tenantId,
        String service,
        String status,
        LocalDate receivedAt) {

    public static PendingRequestResponse from(PriorAuthRequest request) {
        return new PendingRequestResponse(request.id(), request.tenantId(), request.service(), request.status(),
                request.receivedAt());
    }
}
