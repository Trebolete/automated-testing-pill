package com.allianz.automated.testing;

import com.allianz.automated.testing.model.Policy;

import java.util.UUID;

public class Fixtures {

    public static Policy policy() {
        return new Policy(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                "policy info"
        );
    }

}
