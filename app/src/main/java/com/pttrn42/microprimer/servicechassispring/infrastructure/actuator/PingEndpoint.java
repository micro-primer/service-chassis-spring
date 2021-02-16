package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PingEndpoint {

    @GetMapping("/actuator/ping")
    String ping() {
        return "pong";
    }
}
