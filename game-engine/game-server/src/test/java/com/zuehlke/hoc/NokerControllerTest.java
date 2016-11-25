package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NokerControllerTest {

    @Autowired
    private MockMvc mvc;

    // see: https://spring.io/guides/tutorials/bookmarks/
    private HttpMessageConverter mappingJackson2HttpMessageConverter;



    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void register() throws Exception {

        RegisterMessage request = new RegisterMessage()
                .setName("Francis Underwood")
                .setHostname("localhost")
                .setPort(2222);
        send(request);

        request = new RegisterMessage()
                .setName("Flash")
                .setHostname("localhost")
                .setPort(1111);
        send(request);

        request = new RegisterMessage()
                .setName("Mister Bean")
                .setHostname("localhost")
                .setPort(9999);
        send(request);
    }

    private void send(RegisterMessage request) throws Exception {
        String requestJson = json(request);
        this.mvc.perform(
                post("/noker/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
