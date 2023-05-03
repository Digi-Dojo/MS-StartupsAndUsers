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
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andExpect(status().isOk());
        getByName("DigiDojo");
        deleteStartupA();
    }

    @Test
    public void getMappingFindByNameShouldReturnTheStartupWithTheSpecifiedName() throws Exception {
        String startupNameA = new JSONObject(generateStartupA().getResponse().getContentAsString()).getString("name");
        String startupNameB = new JSONObject(generateStartupB().getResponse().getContentAsString()).getString("name");
        JSONObject result = new JSONObject(mockMvc.perform(get("/v1/startup/name/DigiDojo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("DigiDojo"))
                .andReturn()
                .getResponse()
                .getContentAsString());
        assertEquals(startupNameA, result.getString("name"));
        assertNotEquals(startupNameB, result.getString("name"));
        deleteStartupA();
        deleteStartupB();
    }

    @Test
    public void postMappingUpdateStartupNameShouldUpdateTheNameOfTheStartup() throws Exception {
        JSONObject startup = new JSONObject(generateStartupA().getResponse().getContentAsString());
        String oldName = startup.getString("name");
        mockMvc.perform(post("/v1/startup/updateName")
                        .contentType("application/json")
                        .content("{\"oldName\":\"" + oldName + "\",\"newName\":\"NewDigiDojo\"}"))
                .andExpect(status().isOk());
        startup = new JSONObject(getByName("NewDigiDojo"));
        String newName = startup.getString("name"); //it gets DigiDojo instead of NewDigiDojo
        assertEquals("NewDigiDojo", startup.getString("name"));
        deleteAfterNameUpdate();
    }

    @Test
    public void postMappingUpdateStartupDescriptionShouldUpdateTheDescriptionOfTheStartup() throws Exception {
        JSONObject startup = new JSONObject(generateStartupA().getResponse().getContentAsString());
        String oldName = startup.getString("name");
        mockMvc.perform(post("/v1/startup/updateDescription")
                        .contentType("application/json")
                        .content("{\"oldName\":\"" + oldName + "\",\"newName\":\"NewDigiDojo\"}"))
                .andExpect(status().isOk());
        startup = new JSONObject(getByName("NewDigiDojo"));
        String newName = startup.getString("name"); //it gets DigiDojo instead of NewDigiDojo
        assertEquals("NewDigiDojo", startup.getString("name"));
        deleteAfterNameUpdate();
    }

    //need to decide whether to check only the id (like in the team member int test) or all the fields
    //to keep the code consistent
    @Test
    public void getMappingGetAllShouldReturnAllStartups() throws Exception {
        MvcResult result = mockMvc.perform(post("/v1/startup/create")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andReturn();
        JSONObject object1 = new JSONObject(result.getResponse().getContentAsString());
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
        assertEquals(startupId1, object3.getLong("id"));
        assertEquals("DigiDojo", object3.getString("name"));
        assertEquals("a fun way to create startups", object3.getString("description"));
        object3 = array.getJSONObject(1);
        assertEquals(startupId2, object3.getLong("id"));
        assertEquals("LessSuccessfulStartup", object3.getString("name"));
        assertEquals("DigiDojo is better", object3.getString("description"));
        deleteStartupA();
        deleteStartupB();
    }

    private MvcResult generateStartupA() throws Exception {
        return mockMvc.perform(post("/v1/startup/create")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult generateStartupB() throws Exception {
        return mockMvc.perform(post("/v1/startup/create")
                        .contentType("application/json")
                        .content("{\"name\":\"LessSuccessfulStartup\",\"description\":\"DigiDojo is better\"}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    private String getByName(String name) throws Exception {
        return mockMvc.perform(get("/v1/startup/name/{name}", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private void deleteStartupA() throws Exception {
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\"}"))
                .andExpect(status().isOk());
    }

    private void deleteStartupB() throws Exception {
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"LessSuccessfulStartup\"}"))
                .andExpect(status().isOk());
    }

    public void deleteAfterNameUpdate() throws Exception {
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"NewDigiDojo\"}"))
                .andExpect(status().isOk());
    }
}
