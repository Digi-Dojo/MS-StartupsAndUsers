package com.startupsdigidojo.usersandteams.user.application;

import com.startupsdigidojo.usersandteams.user.domain.ManageUsers;
import com.startupsdigidojo.usersandteams.user.domain.SearchUsers;
import com.startupsdigidojo.usersandteams.user.domain.UserRepository;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebAppConfiguration(value = "/Users/matteolarcher/Desktop/UNIBZ/SoftwareSystemsArchitecture/DigiDojo/TeamA/MFront-StartupsAndUsers")
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
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
        this.mockMvc.perform(get("http://localhost:3000")).andDo(print())
                .andExpect(view().name("index"));
    }
}
