package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator;

import com.pttrn42.microprimer.servicechassispring.infrastructure.servlet.WebInitializer;
//import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
//import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
//import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.health.HealthIndicatorAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.info.InfoEndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.logging.LoggersEndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.management.HeapDumpWebEndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.management.ThreadDumpEndpointAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration;
//import org.springframework.boot.actuate.autoconfigure.web.servlet.ServletManagementContextAutoConfiguration;
//import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
//import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
//import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
//import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
//import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
//import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
//import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
//@ImportAutoConfiguration({
//        EndpointAutoConfiguration.class,
//        WebEndpointAutoConfiguration.class,
//        ServletManagementContextAutoConfiguration.class,
//        ManagementContextAutoConfiguration.class,
//
//        HealthIndicatorAutoConfiguration.class,
//
//        InfoEndpointAutoConfiguration.class,
//        HealthEndpointAutoConfiguration.class,
//
//        HeapDumpWebEndpointAutoConfiguration.class,
//        ThreadDumpEndpointAutoConfiguration.class,
//        LoggersEndpointAutoConfiguration.class,
//        PrometheusMetricsExportAutoConfiguration.class,
//})
//@EnableConfigurationProperties(CorsEndpointProperties.class)
class ActuatorConfiguration {

//    @Bean //taken from WebMvcEndpointManagementContextConfiguration.class
//    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
//                                                                         ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
//                                                                         EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
//                                                                         WebEndpointProperties webEndpointProperties) {
//        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
//        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//        allEndpoints.addAll(webEndpoints);
//        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//        EndpointMapping endpointMapping = new EndpointMapping(webEndpointProperties.getBasePath());
//        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes,
//                corsProperties.toCorsConfiguration(),
//                new EndpointLinksResolver(allEndpoints, webEndpointProperties.getBasePath()));
//    }
//
//    @Bean
//    DispatcherServletPath dispatcherServletPath() {
//        return () -> WebInitializer.APPLICATION_ROOT;
//    }

//    @Bean
//    PingEndpoint pingEndpoint() {
//        return new PingEndpoint();
//    }

}
