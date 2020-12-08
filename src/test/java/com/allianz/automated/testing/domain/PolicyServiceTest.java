package com.allianz.automated.testing.domain;

import com.allianz.automated.testing.external.http.PolicyValidationService;
import com.allianz.automated.testing.external.mainframe.PolicyWrapper;
import com.allianz.automated.testing.model.Policy;
import com.allianz.automated.testing.model.exception.NotFoundException;
import com.allianz.automated.testing.model.exception.PolicyNotValidException;
import com.allianz.automated.testing.model.exception.SavePolicyException;
import com.allianz.automated.testing.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PolicyServiceTest {

    private PolicyRepository policyRepository;
    private PolicyWrapper policyWrapper;
    private PolicyValidationService policyValidationService;

    private PolicyService systemUnderTest;

    @BeforeEach
    void setUp() {
        policyRepository = Mockito.mock(PolicyRepository.class);
        policyWrapper = Mockito.mock(PolicyWrapper.class);
        policyValidationService = Mockito.mock(PolicyValidationService.class);
        systemUnderTest = new PolicyService(policyRepository, policyWrapper, policyValidationService);
    }

    @Test
    void GIVEN_invalidPolicy_WHEN_createPolicy_THEN_mustThrowPolicyNotValidException() {
        Policy policy = new Policy();
        when(policyValidationService.isValid(policy)).thenReturn(false);

        assertThrows(
                PolicyNotValidException.class,
                () -> systemUnderTest.createPolicy(policy)
        );
    }

    @Test
    void GIVEN_validPolicy_WHEN_createPolicy_THEN_mustSaveThePolicyInDatabase() {
        Policy policy = new Policy();
        when(policyValidationService.isValid(policy)).thenReturn(true);
        when(policyRepository.save(policy)).thenReturn(policy);

        Policy actual = systemUnderTest.createPolicy(policy);

        assertEquals(actual, policy);
    }

    @Test
    void GIVEN_validPolicy_AND_databaseFails_WHEN_createPolicy_THEN_mustStoreThePolicyThroughMainframe() {
        Policy policy = new Policy();
        when(policyValidationService.isValid(policy)).thenReturn(true);
        when(policyRepository.save(policy)).thenThrow(SavePolicyException.class);
        when(policyWrapper.save(policy)).thenReturn(policy);

        Policy actual = systemUnderTest.createPolicy(policy);

        verify(policyWrapper, times(1)).save(policy);
        assertEquals(actual, policy);
    }

    @Test
    void GIVEN_wrongPolicyId_WHEN_getPolicy_THEN_mustThrowNotFoundException() {
        String policyId = "wrong policy id";
        when(policyRepository.findById(policyId)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> systemUnderTest.getPolicy(policyId)
        );
    }

    @Test
    void GIVEN_validPolicyId_WHEN_getPolicy_THEN_mustReturnThePolicy() {
        String policyId = "wrong policy id";
        Policy expected = new Policy();
        when(policyRepository.findById(policyId)).thenReturn(Optional.of(expected));

        Policy actual = systemUnderTest.getPolicy(policyId);

        assertEquals(actual, expected);
    }

}