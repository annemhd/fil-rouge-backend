package com.filrougeapp.controller;

import com.filrougeapp.model.AuthResponse;
import com.filrougeapp.model.User;
import com.filrougeapp.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(AuthController.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    public AuthControllerTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() throws Exception {
        User user = new User();
        user.setUsername("newuser");

        AuthResponse authResponse = new AuthResponse("token");

        given(authService.register(user)).willReturn(authResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"newuser\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"))
                .andDo(print());
    }

    @Test
    public void testLogin() throws Exception {
        User user = new User();
        user.setUsername("existinguser");

        AuthResponse authResponse = new AuthResponse("token");

        given(authService.authenticate(user)).willReturn(authResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"existinguser\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"))
                .andDo(print());
    }
}
