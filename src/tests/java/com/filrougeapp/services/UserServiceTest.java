package com.filrougeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.filrougeapp.model.User;
import com.filrougeapp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setName("John Doe");

		when(userRepository.save(any(User.class))).thenReturn(user);

		User createdUser = userService.createUser(user);
		assertNotNull(createdUser);
		assertEquals("John Doe", createdUser.getName());
	}

	@Test
	public void testGetUserById() {
		User user = new User();
		user.setId(1L);
		user.setName("John Doe");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		User foundUser = userService.getUserById(1L);
		assertNotNull(foundUser);
		assertEquals("John Doe", foundUser.getName());
	}

	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setId(1L);
		user.setName("John Doe");

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		user.setName("Jane Doe");
		User updatedUser = userService.updateUser(1L, user);
		assertNotNull(updatedUser);
		assertEquals("Jane Doe", updatedUser.getName());
	}

	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setId(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		userService.deleteUser(1L);
		verify(userRepository).deleteById(1L);
	}
}
