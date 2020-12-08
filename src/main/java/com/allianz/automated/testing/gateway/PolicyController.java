package com.allianz.automated.testing.gateway;

import com.allianz.automated.testing.domain.PolicyService;
import com.allianz.automated.testing.model.Policy;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping("api/v1/policies")
    @ResponseStatus(HttpStatus.CREATED)
    public Policy createPolicy(@RequestBody Policy policy) {
        return policyService.createPolicy(policy);
    }

    @GetMapping("api/v1/policies/{policyId}")
    public Policy fetchPolicy(@PathVariable String policyId) {
        return policyService.getPolicy(policyId);
    }

}