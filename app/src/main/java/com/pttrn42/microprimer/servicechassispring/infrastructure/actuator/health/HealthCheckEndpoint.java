package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator.health;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(
        urlPatterns = "/actuator/health",
        loadOnStartup = 1
)
public class HealthCheckEndpoint extends HealthCheckServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        HealthCheckRegistry registry = applicationContext.getBean(HealthCheckRegistry.class);
        config.getServletContext()
                .setAttribute(HEALTH_CHECK_REGISTRY, registry);
        super.init(config);
    }
}
