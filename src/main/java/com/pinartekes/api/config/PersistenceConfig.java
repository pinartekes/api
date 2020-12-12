package com.pinartekes.api.config;

import com.pinartekes.api.audit.AuditAwareImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EntityScan(basePackages = {"com.pinartekes.api.dao.entity"})
@EnableJpaRepositories(basePackages = "com.pinartekes.api.dao",repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class PersistenceConfig {
    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditAwareImpl();
    }
}