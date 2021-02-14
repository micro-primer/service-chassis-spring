package com.pttrn42.microprimer.servicechassispring.infrastructure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;

@RequiredArgsConstructor
public class MeterRegistryPostProcessor implements BeanPostProcessor {
    private final ObjectProvider<MeterBinder> binders;
    private final ObjectProvider<MetricsConfiguration.MeterRegistryCustomizer<MeterRegistry>> meterRegistryCustomizers;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MeterRegistry) {
            var registry = (MeterRegistry)bean;
            meterRegistryCustomizers.forEach(customizer -> customizer.customize(registry));
            binders.orderedStream().forEach((binder) -> binder.bindTo(registry));
        }

        return bean;
    }
}
