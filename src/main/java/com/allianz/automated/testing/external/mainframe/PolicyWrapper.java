package com.allianz.automated.testing.external.mainframe;

import com.allianz.automated.testing.model.Policy;
import com.allianz.automated.testing.model.exception.PolicyNotValidException;
import com.allianz.automated.testing.model.exception.SavePolicyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PolicyWrapper {

    private final HostClient hostClient;

    public Policy save(Policy policy) {
        int hostResponse = hostClient.savePolicy(policy);
        switch (hostResponse) {
            case -1:
                throw new PolicyNotValidException("Host returned " + hostResponse + ". Policy invalidated by mainframe.");
            case 0:
                throw new SavePolicyException("Host returned " + hostResponse + ". System under overload. Try again later, please.");
            case 1:
                return policy;
            default:
                throw new RuntimeException("Host returned an unexpected value: " + hostResponse);
        }
    }

}
