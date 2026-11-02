package com.animestore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccessTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    // --- 1. Anonymous Access Tests ---

    @Test
    @WithAnonymousUser
    @DisplayName("1. Anonymous request to /api/products returns 401 Unauthorized")
    void whenAnonymousGetProductsThenStatus401() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(401, status);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("2. Anonymous request to /api/products/hello/user returns 401 Unauthorized")
    void whenAnonymousGetHelloUserThenStatus401() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products/hello/user"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(401, status);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("3. Anonymous request to /api/products/hello/admin returns 401 Unauthorized")
    void whenAnonymousGetHelloAdminThenStatus401() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products/hello/admin"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(401, status);
    }

    // --- 2. USER Role Access Tests ---

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("4. User accesses own endpoint /api/products/hello/user: 200 OK")
    void whenUserGetHelloUserThenStatus200() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products/hello/user"));
        int status = result.andReturn().getResponse().getStatus();
        String content = result.andReturn().getResponse().getContentAsString();
        assertEquals(200, status);
        assertEquals("Hello User!", content);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("5. User can view products list: 200 OK")
    void whenUserGetProductsThenStatus200() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("6. User is forbidden from admin endpoint: 403 Forbidden")
    void whenUserGetHelloAdminThenStatus403() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products/hello/admin"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(403, status);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("7. User is forbidden from creating products: 403 Forbidden")
    void whenUserPostProductThenStatus403() throws Exception {
        ResultActions result = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Product\",\"franchise\":\"Anime\",\"category\":\"FIGURE\",\"price\":10.0,\"stock\":1}"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(403, status);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("8. User is forbidden from deleting products: 403 Forbidden")
    void whenUserDeleteProductThenStatus403() throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/products/1"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(403, status);
    }

    // --- 3. ADMIN Role Access Tests ---

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("9. Admin accesses admin endpoint: 200 OK")
    void whenAdminGetHelloAdminThenStatus200() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/products/hello/admin"));
        int status = result.andReturn().getResponse().getStatus();
        String content = result.andReturn().getResponse().getContentAsString();
        assertEquals(200, status);
        assertEquals("Hello Admin!", content);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("10. Admin is forbidden from deleting products: 403 Forbidden")
    void whenAdminDeleteProductThenStatus403() throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/products/1"));
        int status = result.andReturn().getResponse().getStatus();
        assertEquals(403, status);
    }
}
