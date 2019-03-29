package org.wecancodeit.sketchflex.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;
import org.wecancodeit.sketchflex.storage.StorageService;

@RunWith(SpringRunner.class)
@WebMvcTest(SketchController.class)

public class SketchControllerMockMVCTest {

	@Resource
	private MockMvc mvc;

	@Mock
	private Sketch sketch1;

	@Mock
	private Sketch sketch2;

	@Mock
	private SketchDeck sketchDeck1;

	@MockBean
	private SketchRepository sketchRepo;
	
	@MockBean
	private SketchDeckRepository sketchDeckRepo;

	@MockBean
	private StorageService storageService;

	@Test
	public void shouldBeOkayForSingleSketch() throws Exception {
		long id = 1;
		when(sketch1.getSketchDeck()).thenReturn(sketchDeck1);
		when(sketchRepo.findById(id)).thenReturn(Optional.of(sketch1));

		mvc.perform(get("/sketch?id=1")).andExpect(status().isOk());
	}

	@Test
	public void shouldNotBeOkayForSingleSketch() throws Exception {
		mvc.perform(get("/sketch?id=1")).andExpect(status().isNotFound());
	}

	@Test
	public void shouldRouteToSingleSketchView() throws Exception {
		long id = 1;
		when(sketch1.getSketchDeck()).thenReturn(sketchDeck1);
		when(sketchRepo.findById(id)).thenReturn(Optional.of(sketch1));

		mvc.perform(get("/sketch?id=1")).andExpect(view().name("single-sketch-template"));
	}

	@Test
	public void shouldAddSingleSketchToModel() throws Exception {
		long id = 1;
		when(sketch1.getSketchDeck()).thenReturn(sketchDeck1);
		when(sketchRepo.findById(id)).thenReturn(Optional.of(sketch1));

		mvc.perform(get("/sketch?id=1")).andExpect(model().attribute("sketch", sketch1));
	}

	@Test
	public void shouldBeOkayForAllSketches() throws Exception {
		mvc.perform(get("/sketches")).andExpect(status().isOk());
	}

	@Test
	public void shouldRouteToAllSketchesView() throws Exception {
		mvc.perform(get("/sketches")).andExpect(view().name("all-sketches-template"));
	}

	@Test
	public void shouldAddAllSketchesIntoModel() throws Exception {
		Collection<Sketch> allSketches = Arrays.asList(sketch1, sketch2);
		when(sketchRepo.findAll()).thenReturn(allSketches);
		when(sketch1.getSketchDeck()).thenReturn(sketchDeck1);
		when(sketch2.getSketchDeck()).thenReturn(sketchDeck1);

		mvc.perform(get("/sketches")).andExpect(model().attribute("sketches", allSketches));
	}
}
