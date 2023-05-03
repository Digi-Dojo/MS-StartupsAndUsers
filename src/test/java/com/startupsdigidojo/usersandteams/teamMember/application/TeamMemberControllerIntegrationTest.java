package com.startupsdigidojo.usersandteams.teamMember.application;
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
        Long startupId = new JSONObject(generateStartup().getResponse().getContentAsString()).getLong("id");
        Long userId = new JSONObject(generateUser1().getResponse().getContentAsString()).getLong("id");
        Long teamMemberId = new JSONObject(generateTeamMember(startupId, userId).getResponse().getContentAsString()).getLong("id");
        mockMvc.perform(get("/v1/teammembers/findByUSIds")
                .contentType("application/json")
                .content("{\"userId\":\"" + userId + "\",\"startupId\":\"" + startupId + "\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teamMemberId));
        deleteTeamMember(teamMemberId);
        mockMvc.perform(get("/v1/teammembers/findByUSIds")
                        .contentType("application/json")
                        .content("{\"userId\":\"" + userId + "\",\"startupId\":\"" + startupId + "\" }"))
                .andExpect(status().isBadRequest());
        deleteStartup();
        deleteUser1();
    }

    @Test
    public void getMappingWithStartupIdReturnsUsersList() throws Exception{
        Long startupId = new JSONObject(generateStartup().getResponse().getContentAsString()).getLong("id");
        Long[] userIds = new Long[2];
        userIds[0] = new JSONObject(generateUser1().getResponse().getContentAsString()).getLong("id");
        userIds[1] = new JSONObject(generateUser2().getResponse().getContentAsString()).getLong("id");
        Long[] teamMemberIds = new Long[2];
        for(int i = 0; i < 2; i++){
            teamMemberIds[i] = new JSONObject(generateTeamMember(startupId, userIds[i]).getResponse().getContentAsString()).getLong("id");
        }
        JSONArray users = new JSONArray(mockMvc.perform(get("/v1/teammembers/startup/{startupId}", startupId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        for (int i = 0; i < 2; i++){
            assertEquals(userIds[i],users.getJSONObject(i).getLong("id"));
        }
        for (int i = 0; i < 2; i++){
            deleteTeamMember(teamMemberIds[i]);
        }
        deleteUser1();
        deleteUser2();
        deleteStartup();
    }

    @Test
    public void deleteMappingShouldDeleteTeamMember() throws Exception{
        Long userId = new JSONObject(generateUser2().getResponse().getContentAsString()).getLong("id");
        Long startupId = new JSONObject(generateStartup().getResponse().getContentAsString()).getLong("id");
        Long teamMemberId = new JSONObject(generateTeamMember(startupId, userId).getResponse().getContentAsString()).getLong("id");
        deleteTeamMember(teamMemberId);
        deleteUser2();
        deleteStartup();
    }

    @Test
    public void getMappingWithIdReturnsTeamMemberWithSpecifiedId() throws Exception{
        Long userIds = new JSONObject(generateUser2().getResponse().getContentAsString()).getLong("id");
        Long startupIds = new JSONObject(generateStartup().getResponse().getContentAsString()).getLong("id");
        Long teamMember1Id = new JSONObject(generateTeamMember(startupIds, userIds).getResponse().getContentAsString()).getLong("id");
        JSONObject teamMembers = new JSONObject(mockMvc.perform(get("/v1/teammembers/{Id}", teamMember1Id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        assertEquals(teamMember1Id,teamMembers.getLong("id"));
        deleteTeamMember(teamMember1Id);
        deleteUser2();
        deleteStartup();
    }

    @Test
    public void postMappingUpdateTeamMemberRoleUpdatesTheRole() throws Exception {
        Long startupId = new JSONObject(generateStartup().getResponse().getContentAsString()).getLong("id");
        Long userId = new JSONObject(generateUser1().getResponse().getContentAsString()).getLong("id");
        Long teamMemberId = new JSONObject(generateTeamMember(startupId, userId).getResponse().getContentAsString()).getLong("id");

        mockMvc.perform(post("/v1/teammembers/update")
                .contentType("application/json")
                .content("{\"id\":\"" + teamMemberId + "\",\"newRole\":\"programmer\"}"))
                .andExpect(status().isOk());
        JSONObject teamMembers = new JSONObject(mockMvc.perform(get("/v1/teammembers/{Id}", teamMemberId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        assertEquals("programmer", teamMembers.get("role"));
        deleteTeamMember(teamMemberId);
        deleteStartup();
        deleteUser1();
    }

    private MvcResult generateTeamMember(Long startupId, Long userId) throws Exception {
        MvcResult result2 = mockMvc.perform(post("/v1/teammembers/create")
                        .contentType("application/json")
                        .content("{\"userId\":\"" + userId + "\",\"role\":\"designer\",\"startupId\":\"" + startupId + "\" }"))
                .andExpect(status().isOk())
                .andReturn();
        return result2;
    }

    private MvcResult generateStartup() throws Exception {
        MvcResult result = mockMvc.perform(post("/v1/startup/create")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andExpect(status().isOk())
                .andReturn();
        return result;
    }

    private MvcResult generateUser1() throws Exception {
        MvcResult result1 = mockMvc.perform(post("/v1/users/create")
                        .contentType("application/json")
                        .content("{\"name\":\"Ernald\",\"mailAddress\":\"enrami@unibz.org\",\"password\":\"passwordErnald\"}"))
                .andExpect(status().isOk())
                .andReturn();
        return result1;
    }

    private MvcResult generateUser2() throws Exception {
        MvcResult result2 = mockMvc.perform(post("/v1/users/create")
                        .contentType("application/json")
                        .content("{\"name\":\"Gianluca\",\"mailAddress\":\"gianluco@bruco.org\",\"password\":\"passwordBruco\"}"))
                .andExpect(status().isOk())
                .andReturn();
        return result2;
    }

    private void deleteTeamMember(Long teamMember1Id) throws Exception {
        mockMvc.perform(delete("/v1/teammembers/delete")
                        .contentType("application/json")
                        .content("{\"id\":\"" + teamMember1Id + "\"}"))
                .andExpect(status().isOk());
    }

    private void deleteStartup() throws Exception {
        mockMvc.perform(delete("/v1/startup/delete")
                        .contentType("application/json")
                        .content("{\"name\":\"DigiDojo\",\"description\":\"a fun way to create startups\"}"))
                .andExpect(status().isOk());
    }

    private void deleteUser1() throws Exception {
        mockMvc.perform(delete("/v1/users/delete")
                        .contentType("application/json")
                        .content("{\"mailAddress\":\"enrami@unibz.org\"}"))
                .andExpect(status().isOk());
    }

    private void deleteUser2() throws Exception {
        mockMvc.perform(delete("/v1/users/delete")
                        .contentType("application/json")
                        .content("{\"mailAddress\":\"gianluco@bruco.org\"}"))
                .andExpect(status().isOk());
    }
}
