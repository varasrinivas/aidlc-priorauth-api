package com.acmehealth.priorauth.aging;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The tenant's holidays (FR1.2): one JSON file per tenant under resources/data, the same list the nightly report uses.
 * A tenant without a file has no holidays, which is the safe default (the age is then slightly higher, never lower).
 */
@Service
public class HolidayCalendar {

    private final JsonMapper mapper;
    private final Map<String, Set<LocalDate>> cache = new ConcurrentHashMap<>();

    public HolidayCalendar(JsonMapper mapper) {
        this.mapper = mapper;
    }

    public Set<LocalDate> forTenant(String tenantId) {
        return cache.computeIfAbsent(tenantId, this::load);
    }

    private Set<LocalDate> load(String tenantId) {
        ClassPathResource file = new ClassPathResource("data/holidays-" + tenantId + "-2026.json");
        if (!file.exists()) {
            return Set.of();
        }
        try (InputStream in = file.getInputStream()) {
            JsonNode root = mapper.readTree(in);
            Set<LocalDate> holidays = new HashSet<>();
            root.get("holidays").forEach(node -> holidays.add(LocalDate.parse(node.asString())));
            return Set.copyOf(holidays);
        } catch (IOException e) {
            throw new IllegalStateException("cannot read holidays for tenant " + tenantId, e);
        }
    }
}
