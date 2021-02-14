package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator;

//import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
//import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
//import org.springframework.stereotype.Component;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Endpoint(id = "ping")
@RestController
class PingEndpoint {

//    @ReadOperation
    @GetMapping("/actuator/ping")
    String ping() {
        return "pong";
    }
}
