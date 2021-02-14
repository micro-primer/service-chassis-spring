package com.pttrn42.microprimer.servicechassispring;

import com.pttrn42.microprimer.servicechassispring.infrastructure.core.YamlPropertySourceFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@ComponentScan(basePackages = "com.pttrn42.microprimer.servicechassispring")
public class ApplicationConfiguration {

}
