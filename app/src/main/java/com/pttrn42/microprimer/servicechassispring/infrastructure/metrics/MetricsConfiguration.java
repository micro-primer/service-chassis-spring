package com.pttrn42.microprimer.servicechassispring.infrastructure.metrics;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.logging.LogbackMetrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
class MetricsConfiguration {
//    TODO: metrics JMX exporter

    @Configuration
    static class MeterRegistryConfiguration {
        @Bean
        public Clock micrometerClock() {
            return Clock.SYSTEM;
        }

        @Bean
        @Primary
        CompositeMeterRegistry compositeMeterRegistry(Clock clock, List<MeterRegistry> registries) {
            return new CompositeMeterRegistry(clock, registries);
        }

        @Bean
        PrometheusMeterRegistry meterRegistry() {
            return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        }
    }

    @Configuration
    static class JvmMetricsConfiguration {
        @Bean
        public JvmGcMetrics jvmGcMetrics() {
            return new JvmGcMetrics();
        }

        @Bean
        public JvmMemoryMetrics jvmMemoryMetrics() {
            return new JvmMemoryMetrics();
        }

        @Bean
        public JvmThreadMetrics jvmThreadMetrics() {
            return new JvmThreadMetrics();
        }

        @Bean
        public ClassLoaderMetrics classLoaderMetrics() {
            return new ClassLoaderMetrics();
        }
    }

    @Configuration
    static class ApplicationMetricsConfiguration {
//        TODO: HttpClientMetricsAutoConfiguration WebMvcMetricsAutoConfiguration

        @Bean
        public LogbackMetrics logbackMetrics() {
            return new LogbackMetrics();
        }
    }

    @Bean
    public static MeterRegistryPostProcessor meterRegistryPostProcessor(ObjectProvider<MeterBinder> meterBinders,
                                                                        ObjectProvider<MeterRegistryCustomizer<MeterRegistry>> meterRegistryCustomizers) {
        return new MeterRegistryPostProcessor(meterBinders, meterRegistryCustomizers);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configureCommonTags(Environment environment) {
        String appName = environment.getRequiredProperty("spring.application.name");
        String hostName = environment.getProperty("JMX_HOSTNAME");

        return registry -> registry.config().commonTags(
                "app", appName,
                "host", hostName != null ? hostName.split("\\.")[0] : "localhost"
        );
    }

    @FunctionalInterface
    interface MeterRegistryCustomizer<T extends MeterRegistry> {
        void customize(T registry);
    }
}

