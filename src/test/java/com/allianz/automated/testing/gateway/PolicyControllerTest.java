package com.allianz.automated.testing.gateway;

import com.allianz.automated.testing.Fixtures;
import com.allianz.automated.testing.domain.PolicyService;
import com.allianz.automated.testing.model.Policy;
import com.allianz.automated.testing.model.exception.NotFoundException;
import com.allianz.automated.testing.model.exception.PolicyNotValidException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PolicyController.class)
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PolicyService policyService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void GIVEN_invalidPolicy_WHEN_createPolicy_THEN_mustReturnBadRequest() throws Exception {
        Policy policy = Fixtures.policy();
        when(policyService.createPolicy(any())).thenThrow(PolicyNotValidException.class);

        mockMvc
                .perform(
                        post("/api/v1/policies")
                                .content(objectMapper.writeValueAsBytes(policy))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void GIVEN_validPolicy_WHEN_createPolicy_THEN_mustReturnCreatedPolicy() throws Exception {
        Policy policy = Fixtures.policy();
        when(policyService.createPolicy(any())).thenReturn(policy);

        mockMvc
                .perform(
                        post("/api/v1/policies")
                                .content(objectMapper.writeValueAsBytes(policy))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(policy)));

    }

    @Test
    void GIVEN_invalidPolicyId_WHEN_fetchPolicy_THEN_mustReturnNotFound() throws Exception {
        String policyId = "invalid policy id";
        when(policyService.getPolicy(policyId)).thenThrow(NotFoundException.class);

        mockMvc
                .perform(get("/api/v1/policies/" + policyId))
                .andExpect(status().isNotFound());

    }

    @Test
    void GIVEN_validPolicyId_WHEN_fetchPolicy_THEN_mustReturnPolicy() throws Exception {
        Policy expected = Fixtures.policy();
        when(policyService.getPolicy(expected.getId())).thenReturn(expected);

        mockMvc
                .perform(get("/api/v1/policies/" + expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));

    }

}