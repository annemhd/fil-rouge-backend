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

import com.filrougeapp.model.Race;
import com.filrougeapp.repository.RaceRepository;

@ExtendWith(MockitoExtension.class)
public class RaceServiceTest {

	@Mock
	private RaceRepository raceRepository;

	@InjectMocks
	private RaceService raceService;

	@Test
	public void testCreateRace() {
		Race race = new Race();
		race.setName("Elf");

		when(raceRepository.save(any(Race.class))).thenReturn(race);

		Race createdRace = raceService.createRace(race);
		assertNotNull(createdRace);
		assertEquals("Elf", createdRace.getName());
	}

	@Test
	public void testGetRaceById() {
		Race race = new Race();
		race.setId(1L);
		race.setName("Elf");

		when(raceRepository.findById(1L)).thenReturn(Optional.of(race));

		Race foundRace = raceService.getRaceById(1L);
		assertNotNull(foundRace);
		assertEquals("Elf", foundRace.getName());
	}

	@Test
	public void testUpdateRace() {
		Race race = new Race();
		race.setId(1L);
		race.setName("Elf");

		when(raceRepository.findById(1L)).thenReturn(Optional.of(race));
		when(raceRepository.save(any(Race.class))).thenReturn(race);

		race.setName("Orc");
		Race updatedRace = raceService.updateRace(1L, race);
		assertNotNull(updatedRace);
		assertEquals("Orc", updatedRace.getName());
	}

	@Test
	public void testDeleteRace() {
		Race race = new Race();
		race.setId(1L);

		when(raceRepository.findById(1L)).thenReturn(Optional.of(race));

		raceService.deleteRace(1L);
		verify(raceRepository).deleteById(1L);
	}
}
