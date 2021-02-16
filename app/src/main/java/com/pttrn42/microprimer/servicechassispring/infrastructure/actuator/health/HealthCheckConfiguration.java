package com.pttrn42.microprimer.servicechassispring.infrastructure.actuator.health;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.io.File;
import java.util.Map;

@Configuration
class HealthCheckConfiguration implements BeanPostProcessor {

    @Bean
    HealthCheckRegistry healthCheckRegistry() {
        return new HealthCheckRegistry();
    }

    @Bean
    HealthCheckRegistryPostProcessor healthCheckRegistryPostProcessor(Map<String, HealthCheck> healthChecks) {
        return new HealthCheckRegistryPostProcessor(healthChecks);
    }

    @Bean
    HealthCheck diskSpaceHealthCheck() {
        return new DiskSpaceHealthCheck(new File("."), DataSize.ofMegabytes(10));
    }

    @RequiredArgsConstructor
    private static class HealthCheckRegistryPostProcessor implements BeanPostProcessor {
        private final Map<String, HealthCheck> healthChecks;

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean instanceof HealthCheckRegistry) {
                var registry = (HealthCheckRegistry)bean;
                healthChecks.entrySet()
                        .forEach(e -> registry.register(e.getKey(), e.getValue()));
            }

            return bean;
        }
    }
}
