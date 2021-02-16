package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator;

import com.codahale.metrics.servlets.ThreadDumpServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        urlPatterns = "/actuator/threaddump/*",
        loadOnStartup = 1
)
public class ThreaddumpEndpoint extends ThreadDumpServlet {
}
