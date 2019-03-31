package org.wecancodeit.sketchflex.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;


public class SketchDeckControllerTest {
	
	
	@InjectMocks
	private SketchDeckController sketchDeckControl;
	
	@Mock
	private SketchDeck sketchDeck1;
	
	@Mock
	private SketchDeck sketchDeck2;
	
	@Mock
	private SketchDeckRepository sketchDeckRepo;
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddSingleSketchDeckToModel() throws SketchDeckNotFoundException{
		long id = 1;
		when(sketchDeckRepo.findById(id)).thenReturn(Optional.of(sketchDeck1));
		
		sketchDeckControl.findOneSketchDeck(id, model);
		verify(model).addAttribute("sketchdeck",sketchDeck1);
	}
	
	@Test
	public void shouldAddAllSketchDecksToModel() throws Exception{
		Collection<SketchDeck> allSketchDecks = Arrays.asList(sketchDeck1, sketchDeck2);
		when(sketchDeckRepo.findAll()).thenReturn(allSketchDecks);
		
		sketchDeckControl.findAllSketches(model);
		verify(model).addAttribute("sketchDecks",allSketchDecks);
	}

}
