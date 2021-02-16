package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator;

import org.jolokia.http.AgentServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        urlPatterns = "/actuator/jolokia/*",
        loadOnStartup = 1
)
public class JolokiaEndpoint extends AgentServlet {

}
