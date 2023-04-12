package com.startupsdigidojo.usersandteams.startup.application;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class StartupControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesStartupController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("startupController"));
    }

    @Test
    public void postMappingCreatesStartup() throws Exception {
        mockMvc.perform(post("/v1/startup/create")
                .contentType("application/json")
                .content("{\"name\":\"DigiDojo3\",\"description\":\"a very fun way to create startups\"}"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/v1/startup/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("DigiDojo3"));
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo3\",\"description\":\"a very fun way to create startups\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getMappingGetAllShouldReturnAllStartups() throws Exception{
        mockMvc.perform(get("/v1/startup/getAll"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
