package com.allianz.automated.testing.external.mainframe;

import com.allianz.automated.testing.model.Policy;
import org.springframework.stereotype.Component;

@Component
public class HostClient {

    /**
     * @param policy
     * @return -1 when policy is invalid, 0 when host is overloaded, 1 for OK
     */
    int savePolicy(Policy policy) {
        return 1;
    }

}
