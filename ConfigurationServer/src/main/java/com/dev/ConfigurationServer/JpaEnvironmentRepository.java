package com.dev.ConfigurationServer;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JpaEnvironmentRepository implements EnvironmentRepository {

    private final ConfigPropertyRepository repository;

    public JpaEnvironmentRepository(ConfigPropertyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        // Default label to 'main' if it comes in as null
        String finalLabel = (label == null) ? "main" : label;

        Environment environment = new Environment(application, profile);

        // Fetch properties from DB
        List<ConfigProperty> props = repository.findByApplicationAndProfileAndLabel(application, profile, finalLabel);

        // Convert List to a Map for Spring Cloud
        Map<String, String> propertyMap = props.stream()
                .collect(Collectors.toMap(ConfigProperty::getPropKey, ConfigProperty::getPropValue));

        // Add the map to a property source
        environment.add(new PropertySource("jpa-db-source", propertyMap));

        return environment;
    }
}