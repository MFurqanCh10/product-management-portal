package com.furqan.crud_demo_inventory.repository;

import com.furqan.crud_demo_inventory.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Configuration findByParameterName(String parameterName);
}

