package com.filrougeapp.controller;

import com.filrougeapp.model.User;
import com.filrougeapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)  // spécifie que nous testons le contrôleur UserController
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;  // utilisé pour simuler des requêtes HTTP

    @Mock
    private UserRepository userRepository;  // mock de UserRepository pour ne pas interagir avec la vraie base de données

    @InjectMocks
    private UserController userController;  // injecte les mocks dans le contrôleur

    public UserControllerTests() {
        MockitoAnnotations.openMocks(this);  // initialise les mocks
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // vérifie que la réponse HTTP est 200 OK
                .andDo(print());  // affiche la réponse pour le débogage
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");

        given(userRepository.findById(1)).willReturn(java.util.Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // vérifie que la réponse HTTP est 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testuser"))  // vérifie que le champ "username" est "testuser"
                .andDo(print());
    }
}

