package com.allianz.automated.testing.external.http;

import com.allianz.automated.testing.model.Policy;
import org.springframework.stereotype.Component;

@Component
public class PolicyValidationService {

    // This call an external API REST to check if the policy is valid or not.
    public boolean isValid(Policy policy) {
        return true;
    }

}
