package com.backbase.example;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.backbase.buildingblocks.testutils.TestTokenUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * A wrapper annotation for use with integration tests.
 * <p>
 * By default, assumes the integration test modifies the {@link ApplicationContext} associated with the test/s and will
 * therefore be closed and removed from the context cache at the end of the class.
 */
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class ExampleControllerIT {

    public static final String TEST_JWT =
        "{\n"
            + "  \"Role\" : [\n"
            + "    \"Manager\",\n"
            + "    \"Project Administrator\"\n"
            + "  ],\n"
            + "  \"inuid\" : \"Jimmy\",\n"
            + "  \"aud\" : \"www.example.com\",\n"
            + "  \"sub\" : \"jrocket@example.com\",\n"
            + "  \"Email\" : \"jrocket@example.com\",\n"
            + "  \"iss\" : \"Online JWT Builder\",\n"
            + "  \"GivenName\" : \"Johnny\",\n"
            + "  \"exp\" : 1516356196,\n"
            + "  \"iat\" : 1484820196,\n"
            + "  \"Surname\" : \"Rocket\"\n"
            + "}";
    @Autowired
    private MockMvc mvc;

    @Test
    public void exampleTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/client-api/v1/all-messages")
            .header("Authorization", "Bearer "+TestTokenUtil.encode(TEST_JWT));

        ResultActions result = mvc.perform(requestBuilder);

        // Then the request is successful
        result.andExpect(status().isOk());

        List<com.backbase.example.api.client.v1.model.Message> message = new ObjectMapper()
            .readValue(result.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertThat(message).isNotEmpty();
        assertThat(message.get(0).getMessage()).isNotNull();
        assertThat(message.get(0).getId()).isNotNull();
    }
}
