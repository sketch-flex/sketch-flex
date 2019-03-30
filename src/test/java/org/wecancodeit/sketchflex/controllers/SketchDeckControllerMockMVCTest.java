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
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.storage.StorageService;
import org.wecancodeit.sketchflex.controllers.SketchDeckController;


@RunWith(SpringRunner.class)
@WebMvcTest(SketchDeckController.class)
public class SketchDeckControllerMockMVCTest {
 
	
  @Resource
  private MockMvc mvc;
  
  @Mock
  private SketchDeck sketchDeck1;
  
  @Mock
  private SketchDeck sketchDeck2;
	
  @MockBean
  private SketchDeckRepository sketchDeckRepo;
  
  @MockBean  //Tests failed without this even though it's not being tested...do not delete
  private StorageService storageService;
  
  @Test 
  public void shouldBeOkayForSingleSketchDeck() throws Exception
  {
   long id = 1;
   when(sketchDeckRepo.findById(id)).thenReturn(Optional.of(sketchDeck1));
   
   mvc.perform(get("/sketchdeck?id=1")).andExpect(status().isOk());
  }
  
  @Test 
  public void shouldNotBeOkayForSingleSketchDeck() throws Exception
  {
	mvc.perform(get("/sketchdeck?id=1")).andExpect(status().isNotFound());  
  }
  
  @Test
  public void shouldRouteToSingleSketchDeckView() throws Exception
  {
   long id = 1;
   when(sketchDeckRepo.findById(id)).thenReturn(Optional.of(sketchDeck1));
   
   mvc.perform(get("/sketchdeck?id=1")).andExpect(view().name("single-sketchdeck-template"));
  }
  
  @Test
  public void shouldAddSingleSketchDeckToModel() throws Exception
  {
   long id = 1;
   when(sketchDeckRepo.findById(id)).thenReturn(Optional.of(sketchDeck1));
   
   mvc.perform(get("/sketchdeck?id=1")).andExpect(model().attribute("sketchdeck",sketchDeck1));
  }
  
  @Test
  public void shouldBeOkayForAllSketchDecks() throws Exception
  {
   mvc.perform(get("/sketchdecks")).andExpect(status().isOk());
  }
  
  @Test
  public void shouldRouteToAllSketchDecksView() throws Exception
  {
   mvc.perform(get("/sketchdecks")).andExpect(view().name("all-sketchdecks-template"));
  }
  
  @Test
  public void shouldAddAllSketchDecksIntoModel() throws Exception
  {
   Collection<SketchDeck> allSketchDecks = Arrays.asList(sketchDeck1,sketchDeck2); 
   when(sketchDeckRepo.findAll()).thenReturn(allSketchDecks);
	  
   mvc.perform(get("/sketchdecks")).andExpect(model().attribute("sketchDecks",allSketchDecks));
  }
  
  
  
}
