package com.startupsdigidojo.usersandteams.teamMember.application;
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
public class TeamMemberControllerIntegrationTest {
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
        assertNotNull(webApplicationContext.getBean("teamMemberController"));
    }

    @Test
    public void postMappingCreatesTeamMember() throws Exception {
        mockMvc.perform(post("/v1/teammembers/create")
                .contentType("application/json")
                .content("{\"userId\":\"3\",\"role\":\"designer\",\"startupId\":\"2\" }"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/v1/teammembers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Id").value("1"));
        mockMvc.perform(delete("/v1/users/delete")
                        .contentType("application/json")
                        .content("{\"userId\":\"3\",\"role\":\"designer\",\"startupId\":\"2\" }"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getMappingWithStartupIdReturnsUsersList() throws Exception{
        mockMvc.perform(get("/v1/teammembers/startup/{startupId}", "2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMappingShouldDeleteTeamMember() throws Exception{
        mockMvc.perform(delete("/v1/teammembers/delete")
                .contentType("application/json")
                .content("{\"id\":\"3\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
