package com.allianz.automated.testing.domain;


import com.allianz.automated.testing.external.http.PolicyValidationService;
import com.allianz.automated.testing.external.mainframe.PolicyWrapper;
import com.allianz.automated.testing.model.Policy;
import com.allianz.automated.testing.model.exception.NotFoundException;
import com.allianz.automated.testing.model.exception.PolicyNotValidException;
import com.allianz.automated.testing.model.exception.SavePolicyException;
import com.allianz.automated.testing.repository.PolicyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final PolicyWrapper policyWrapper;
    private final PolicyValidationService policyValidationService;

    public Policy createPolicy(Policy policy) {
        if (policyValidationService.isValid(policy)) {
            try {
                return policyRepository.save(policy);
            } catch (SavePolicyException exception) {
                return policyWrapper.save(policy);
            }
        }
        throw new PolicyNotValidException("Policy not valid. Please, review provided DTO");
    }

    public Policy getPolicy(String policyId) {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new NotFoundException("Policy not found. Provided policyId: " + policyId));
    }

}
