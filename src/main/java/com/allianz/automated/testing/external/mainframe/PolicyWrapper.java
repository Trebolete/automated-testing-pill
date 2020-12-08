package com.allianz.automated.testing.external.mainframe;

import com.allianz.automated.testing.model.Policy;
import org.springframework.stereotype.Component;

@Component
public class PolicyWrapper {

    // Policy storage through mainframe. This, of course, always works.
    public Policy save(Policy policy) {
        return new Policy(policy);
    }

}
