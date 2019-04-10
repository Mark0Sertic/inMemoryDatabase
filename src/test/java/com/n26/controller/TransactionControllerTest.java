package com.n26.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.n26.Application;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.springframework.http.MediaType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void contextLoads() {
        assertNotNull(this.webApplicationContext);
    }

    @Test
    public void invalidTimestampRequestOutdatedTest() throws Exception {
        Instant currentTimestamp = Instant.now().minusMillis(70000);
        String time = DateTimeFormatter.ISO_INSTANT.format(currentTimestamp);
        String json = createJson(time);

        this.mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void validTimestampRequestTest() throws Exception {
        Instant currentTimestamp = Instant.now().minusMillis(2000);
        String time = DateTimeFormatter.ISO_INSTANT.format(currentTimestamp);
        String json = createJson(time);

        this.mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTransactions() throws Exception {
        this.mockMvc.perform(delete("/transactions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private  String createJson(String timestamp){
        return String.format("{\"amount\": \"12.43\",\"timestamp\": \"%s\" }", timestamp);
    }

}
