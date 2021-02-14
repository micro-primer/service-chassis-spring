package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrometheusEndpoint {
    private final PrometheusMeterRegistry prometheusMeterRegistry;

    @GetMapping("/actuator/prometheus")
    public String prometheusScrape() {
        return prometheusMeterRegistry.scrape();
    }
}
