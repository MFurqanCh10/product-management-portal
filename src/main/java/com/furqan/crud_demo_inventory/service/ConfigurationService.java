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
        Configuration config = configurationRepository.findByParameterNameIgnoreCase("Currency Unit");
        if (config != null) {
            String value = config.getParameterValue();
            // Map PKR → Rs.
            if ("PKR".equalsIgnoreCase(value)) {
                return "Rs.";
            }
            return value; // fallback if other currency
        }
        return "";
    }
}