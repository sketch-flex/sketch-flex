package org.wecancodeit.sketchflex.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

public class SketchControllerTest {

	@InjectMocks
	private SketchController underTest;

	@Mock
	private SketchRepository sketchRepo;
	
	@Mock
	private SketchDeckRepository sketchDeckRepo;

	@Mock
	private Sketch sketch1;

	@Mock
	private Sketch sketch2;

	@Mock
	private SketchDeck sketchDeck;

	@Mock
	private Model model;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldAddAllSketchesToModel() {
		Collection<Sketch> allSketches = Arrays.asList(sketch1, sketch2);
		when(sketchRepo.findAll()).thenReturn(allSketches);
		when(sketchDeckRepo.findAll()).thenReturn(Arrays.asList(sketchDeck));
		underTest.findAllSketches(model);
		verify(model).addAttribute("sketches", allSketches);
	}

	@Test
	public void shouldAddOneSketchToModel() throws SketchNotFoundException {
		Long id = 1L;
		when(sketchRepo.findById(id)).thenReturn(Optional.of(sketch1));

		underTest.findOneSketch(id, model);
		verify(model).addAttribute("sketch", sketch1);
	}

	@Test
	public void shouldAddAdditionalSketchToModel() {
		String name = "name";
		String address = "address";
		underTest.addSketch(name, address, sketchDeck);
		ArgumentCaptor<Sketch> sketchArgument = ArgumentCaptor.forClass(Sketch.class);
		verify(sketchRepo).save(sketchArgument.capture());
		assertEquals("name", sketchArgument.getValue().getName());
	}

	@Test
	public void shouldAddAllSketchesInSketchDeckToModel() throws SketchNotFoundException {
		Long id = 1L;
		List<Sketch> allSketches = Arrays.asList(sketch1, sketch2);
		when(sketchRepo.findById(id)).thenReturn(Optional.of(sketch1));
		when(sketch1.getSketchDeck()).thenReturn(sketchDeck);
		when(sketchRepo.findAllBySketchDeck(sketchDeck)).thenReturn(allSketches);
		underTest.findOneSketch(id, model);
		verify(model).addAttribute("sketches", allSketches);
		
	}
}
