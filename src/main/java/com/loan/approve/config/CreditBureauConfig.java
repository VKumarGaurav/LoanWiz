package com.loan.approve.config;


import com.loan.approve.util.CreditBureauType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "credit.bureau")
@Data
public class CreditBureauConfig {
    private Map<String, String> endpoints;
    private String apiKey;
    private int timeoutSeconds;

    public String getBureauEndpoint(CreditBureauType bureauType) {
        return endpoints.get(bureauType.name().toLowerCase());
    }

}
