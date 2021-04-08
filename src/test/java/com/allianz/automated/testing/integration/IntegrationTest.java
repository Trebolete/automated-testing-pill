package com.allianz.automated.testing.integration;

import com.allianz.automated.testing.AutomatedTestingApplication;
import com.allianz.automated.testing.Fixtures;
import com.allianz.automated.testing.external.http.PolicyValidationService;
import com.allianz.automated.testing.external.mainframe.PolicyWrapper;
import com.allianz.automated.testing.model.Policy;
import com.allianz.automated.testing.repository.PolicyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(
        classes = AutomatedTestingApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PolicyRepository policyRepository;

    @MockBean
    private PolicyValidationService policyValidationService;

    @MockBean
    private PolicyWrapper policyWrapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void GIVEN_invalidPolicy_WHEN_createPolicy_THEN_mustReturnBadRequest() {
        Policy invalidPolicy = Fixtures.policy();
        given(policyValidationService.isValid(any())).willReturn(false);

        ResponseEntity<Policy> actual = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/policies",
                invalidPolicy,
                Policy.class
        );

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void GIVEN_validPolicy_WHEN_createPolicy_THEN_mustReturnCreated_andMustCreatePolicy() {
        Policy expected = Fixtures.policy();
        given(policyValidationService.isValid(any())).willReturn(true);

        ResponseEntity<Policy> actual = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/policies",
                expected,
                Policy.class
        );

        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    void GIVEN_invalidPolicyId_WHEN_fetchPolicy_THEN_mustReturnNotFoundError() {
        String invalidPolicyId = "not a policy id";
        ResponseEntity<Policy> actual = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/policies/" + invalidPolicyId,
                Policy.class
        );

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void GIVEN_validPolicyId_WHEN_fetchPolicy_THEN_mustReturnPolicy() {
        Policy expected = policyRepository.save(Fixtures.policy());

        ResponseEntity<Policy> actual = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/policies/" + expected.getId(),
                Policy.class
        );

        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}