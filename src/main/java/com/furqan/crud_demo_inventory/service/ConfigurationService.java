package com.furqan.crud_demo_inventory.service;

import com.furqan.crud_demo_inventory.entity.Configuration;
import com.furqan.crud_demo_inventory.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public String getCurrencyUnit() {
        Configuration config = configurationRepository.findByParameterName("Currency Unit");
        return config != null ? config.getParameterValue() : "";
    }
}