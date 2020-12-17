package com.allianz.automated.testing.repository;

import com.allianz.automated.testing.Fixtures;
import com.allianz.automated.testing.model.Policy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PolicyRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PolicyRepository systemUnderTest;

    @Test
    void GIVEN_existingPolicyId_WHEN_findById_THEN_mustReturnThePolicy() {
        Policy expected = testEntityManager.persist(Fixtures.policy());

        Optional<Policy> actual = systemUnderTest.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).isEqualTo(expected);
    }

}