package com.pttrn42.microprimer.servicechassispring.infrastructure.tracing;

import brave.CurrentSpanCustomizer;
import brave.SpanCustomizer;
import brave.Tracing;
import brave.context.slf4j.MDCScopeDecorator;
import brave.handler.SpanHandler;
import brave.http.HttpTracing;
import brave.propagation.CurrentTraceContext;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.sampler.Sampler;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zipkin2.reporter.Sender;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

import java.net.URL;

@Slf4j
@Configuration
@Import(SpanCustomizingAsyncHandlerInterceptor.class)
public class TracingConfiguration {

    /** Creates client spans for HTTP requests. */
    @Bean // RestTemplate need to be declared in order to be instrumented
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    /** Allows log patterns to use {@code %{traceId}} {@code %{spanId}} and {@code %{userName}} */
    @Bean
    CurrentTraceContext.ScopeDecorator correlationScopeDecorator() {
        return MDCScopeDecorator.newBuilder()
                .build();
    }

    /** Propagates trace context between threads. */
    @Bean
    CurrentTraceContext currentTraceContext(CurrentTraceContext.ScopeDecorator correlationScopeDecorator) {
        return ThreadLocalCurrentTraceContext.newBuilder()
                .addScopeDecorator(correlationScopeDecorator)
                .build();
    }

    /** Configuration for how to send spans to Zipkin */
    @Bean
    @Conditional(ValidZipkinUrlCondition.class)
    Sender sender(
            @Value("${spring.zipkin.baseUrl}/api/v2/spans") String zipkinEndpoint) {
        return URLConnectionSender.create(zipkinEndpoint);
    }

    /** Configuration for how to buffer spans into messages for Zipkin */
    @Bean @Primary
    @Conditional(ValidZipkinUrlCondition.class)
    SpanHandler zipkinSpanHandler(Sender sender) {
        return AsyncZipkinSpanHandler.create(sender);
    }

    /** NoOp SpanHandler when no url defined */
    @Bean
    SpanHandler noopSpanHandler() {
        return SpanHandler.NOOP;
    }

    @Bean
    Sampler sampler(@Value("${spring.sleuth.sampler.probability}") String probability) {
        return Sampler.create(Float.parseFloat(probability));
    }

    /** Controls aspects of tracing such as the service name that shows up in the UI */
    @Bean
    Tracing tracing(
            @Value("${spring.application.name}") String serviceName,
            @Value("${brave.supportsJoin:true}") boolean supportsJoin,
            @Value("${brave.traceId128Bit:false}") boolean traceId128Bit,
            Sampler sampler,
            CurrentTraceContext currentTraceContext,
            SpanHandler zipkinSpanHandler) {
        return Tracing.newBuilder()
                .localServiceName(serviceName)
                .supportsJoin(supportsJoin)
                .traceId128Bit(traceId128Bit)
                .currentTraceContext(currentTraceContext)
                .sampler(sampler)
                .addSpanHandler(zipkinSpanHandler)
                .build();
    }

    /** Allows someone to add tags to a span if a trace is in progress. */
    @Bean
    SpanCustomizer spanCustomizer(Tracing tracing) {
        return CurrentSpanCustomizer.create(tracing);
    }

    /** Decides how to name and tag spans. By default they are named the same as the http method. */
    @Bean HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

    /** Adds MVC Controller tags to server spans */
    @Bean
    WebMvcConfigurer tracingWebMvcConfigurer(
            final SpanCustomizingAsyncHandlerInterceptor webMvcTracingCustomizer) {
        return new WebMvcConfigurer() {
            /** Adds application-defined web controller details to HTTP server spans */
            @Override public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(webMvcTracingCustomizer);
            }
        };
    }

    static class ValidZipkinUrlCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String zipkinBaseUrl = context.getEnvironment().getProperty("spring.zipkin.baseUrl");
            try {
                new URL(zipkinBaseUrl).toURI();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
