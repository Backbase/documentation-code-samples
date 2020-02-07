package com.backbase.example;


import com.backbase.service.example.rest.spec.v1.message.MessagePostRequestBody;
import com.backbase.service.example.rest.spec.v1.message.MessagePostResponseBody;
import com.backbase.service.example.rest.spec.v1.message.MessagesGetResponseBody;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;
import org.junit.runner.RunWith;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * A wrapper annotation for use with integration tests.
 *
 * By default, assumes the integration test modifies the
 * {@link ApplicationContext} associated with the test/s and will therefore be
 * closed and removed from the context cache at the end of the class.
 */
@SpringBootTest(classes = Application.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class ExampleControllerIT {

    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    public static final String TEST_JWT =
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsIm5hZiI6MTUwNjUyNzQzMSwiY25leHAiOnRydWUsImdycCI6" +
                            "WyJhZG1pbihBRE1JTikiXSwiYW5sb2MiOnRydWUsImFuZXhwIjp0cnVlLCJlbmJsIjp0cnVlLCJleHAiOjE1MDY1" +
                            "MjU5MzEsImlhdCI6MTUwNjUyNTYzMSwicm9sIjpbIlJPTEVfQUNUVUFUT1IiLCJST0xFX0FETUlOIiwiUk9MRV9nc" +
                            "m91cF9hZG1pbihBRE1JTikiXSwianRpIjoiMjQ1MDBkNDQtNDRiZS00MTI1LTg4MjctOWY1NDAwOTc4NmEzIn0.5qZ" +
                            "nncyJpWJ8GqklJK6RmyHUUPddNOh52al65_C4T9o";
    @Autowired
    private MockMvc mvc;

    @Test
    public void exampleTest() throws Exception {
        String greetingsId = givenAGreetingExists();
        whenWeGetTheMessageByIdThenTheMessageExists(greetingsId);
        WhenWeGetAllMessagesThenAListOfMessagesIsReturned();
    }

    private void WhenWeGetAllMessagesThenAListOfMessagesIsReturned() throws Exception {
        //When:  Request for all messages
        MockHttpServletRequestBuilder getAllMessagesRequestBuilder = get("/v1/message/messages")
                .header("Authorization", TEST_JWT);

        ResultActions getAllMessagesResult = mvc.perform(getAllMessagesRequestBuilder).andDo(print());

        // Then the request is successful with a list of messages
        getAllMessagesResult.andExpect(status().isOk());

        List<MessagesGetResponseBody> listOfMessages = new ObjectMapper().readValue(getAllMessagesResult.andReturn().getResponse().getContentAsString(),
                new TypeReference<List<MessagesGetResponseBody>>() {});

        assertThat(listOfMessages.size(), greaterThan(0));
    }

    private void whenWeGetTheMessageByIdThenTheMessageExists(String greetingsId) throws Exception {
        //When: we get that message
        MockHttpServletRequestBuilder getMessageRequestBuilder = get("/v1/message?id=" + greetingsId)
                .header("Authorization", TEST_JWT);

        ResultActions result = mvc.perform(getMessageRequestBuilder).andDo(print());

        result = result.andExpect(status().isOk());

        MvcResult response = result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        String responseBody = response.getResponse().getContentAsString();

        //Then: the message exists
        assertThat("ID in response should match ID in request", responseBody, containsString("Hello World"));
    }

    private String givenAGreetingExists() throws Exception {
        //Given: a message exists
        String id = UUID.randomUUID().toString();
        MessagePostRequestBody message = new MessagePostRequestBody();
        message.setMessage("Hello World is it me your looking for?");
        message.setId(id);

        String messageAsString = new ObjectMapper().writeValueAsString(message);

        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

        MockHttpServletRequestBuilder postRequestBuilder = post("/v1/message")
                .header("Authorization", TEST_JWT)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(messageAsString);

        ResultActions postResult = mvc.perform(postRequestBuilder).andDo(print());

        postResult.andExpect(status().isCreated());

        MessagePostResponseBody messagePostResponseBody = new ObjectMapper().readValue(postResult.andReturn().getResponse().getContentAsString(),
                new TypeReference<MessagePostResponseBody>() {});
        return messagePostResponseBody.getId();
    }
}
