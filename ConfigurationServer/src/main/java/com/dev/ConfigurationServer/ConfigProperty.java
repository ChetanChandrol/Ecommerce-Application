package com.dev.ConfigurationServer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PROPERTIES")
@Data
public class ConfigProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String application;
    private String profile;
    private String label;
    private String propKey;
    private String propValue;
}