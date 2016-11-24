package com.zuehlke.houseofcards;

import com.zuehlke.houseofcards.dto.Bot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NokerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void register() throws Exception {
        /*
        Bot bot = new Bot()
                .setName("Francis Underwood")
                .setHostname("localhost")
                .setPort(2222);

        this.mvc.perform(post("/noker/register").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(""));

        */

    }
}
