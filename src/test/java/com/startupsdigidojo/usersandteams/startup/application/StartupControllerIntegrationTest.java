package com.startupsdigidojo.usersandteams.startup.application;
import jakarta.servlet.ServletContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class StartupControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();}

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
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/v1/startup/name/DigiDojo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("DigiDojo"));
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andExpect(status().isOk());
    }

    //need to decide whether to check only the id (like in the team member int test) or all the fields
    //to keep the code consistent
    @Test
    public void getMappingGetAllShouldReturnAllStartups() throws Exception{
        MvcResult result1 = mockMvc.perform(post("/v1/startup/create")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andReturn();
        JSONObject object1 = new JSONObject(result1.getResponse().getContentAsString());
        Long startupId1 = object1.getLong("id");
        MvcResult result2 = mockMvc.perform(post("/v1/startup/create")
                        .contentType("application/json")
                        .content("{\"name\":\"LessSuccessfulStartup\",\"description\":\"DigiDojo is better\"}"))
                .andReturn();
        JSONObject object2 = new JSONObject(result2.getResponse().getContentAsString());
        Long startupId2 = object2.getLong("id");
        MvcResult result3 = mockMvc.perform(get("/v1/startup/getAll"))
                .andExpect(status().isOk())
                .andReturn();
        JSONArray array = new JSONArray(result3.getResponse().getContentAsString());
        JSONObject object3 = array.getJSONObject(0);
        assertEquals(startupId1,object3.getLong("id"));
        assertEquals("DigiDojo",object3.getString("name"));
        assertEquals("a fun way to create startups",object3.getString("description"));
        object3 = array.getJSONObject(1);
        assertEquals(startupId2,object3.getLong("id"));
        assertEquals("LessSuccessfulStartup",object3.getString("name"));
        assertEquals("DigiDojo is better",object3.getString("description"));
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"LessSuccessfulStartup\",\"description\":\"DigiDojo is better\"}"))
                .andExpect(status().isOk());
    }


}
