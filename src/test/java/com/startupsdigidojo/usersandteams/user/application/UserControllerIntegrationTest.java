package com.startupsdigidojo.usersandteams.user.application;

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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @Test
    public void givenWac_whenServletContext_thenItProvidesUserController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("userController"));
    }

    @Test
    public void getMappingWithEmailReturnsUserWithIndicatedEmail() throws Exception {
        mockMvc.perform(get("/v1/users/matteo.larcer@gmail.com")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mailAddress").value("matteo.larcer@gmail.com"));
    }

    @Test
    public void postMappingCreatesUser() throws Exception {
        mockMvc.perform(post("/v1/users/create")
                .contentType("application/json")
                .content("{\"name\":\"Ernald\",\"mailAddress\":\"enrami@unibz.org\",\"password\":\"passwordErnald\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

