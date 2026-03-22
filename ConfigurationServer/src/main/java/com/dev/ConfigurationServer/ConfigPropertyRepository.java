package com.dev.ConfigurationServer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigPropertyRepository extends JpaRepository<ConfigProperty, Long> {
    List<ConfigProperty> findByApplicationAndProfileAndLabel(String app, String profile, String label);
}