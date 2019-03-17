package org.wecancodeit.sketchflex.models;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;
import org.wecancodeit.sketchflex.storage.StorageService;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsSketchTest {

	@MockBean
	private StorageService storageService;
	
	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private SketchDeckRepository sketchDeckRepo;
	
	@Resource
	private SketchRepository sketchRepo;
 
	
	@Test
	public void shouldBeAbleToSaveASketchDeck() {
		SketchDeck sketchDeck1 = new SketchDeck("Start");
		sketchDeckRepo.save(sketchDeck1);
		Long id = sketchDeck1.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<SketchDeck> sketchResult = sketchDeckRepo.findById(id);
		sketchDeck1 = sketchResult.get();
		assertThat(sketchDeck1.getName(), is("Start"));
		}
	
	@Test
	public void shouldBeAbleToSaveAndLoadASketch(){
		SketchDeck sketchDeck1 = new SketchDeck("Start");
		sketchDeckRepo.save(sketchDeck1);
		Sketch sketch1 = new Sketch("First", "address", sketchDeck1);
		sketchRepo.save(sketch1);
		Long id = sketch1.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Sketch> sketchResult = sketchRepo.findById(id);
		sketch1 = sketchResult.get();
		assertThat(sketch1.getName(), is("First"));
	}
	
	@Test
	public void shouldEstablishRelationshipBetweenSketchAndSketchDeck() {
		SketchDeck sketchDeck1 = new SketchDeck("Start");
		sketchDeckRepo.save(sketchDeck1);
		Long id = sketchDeck1.getId();
		
		Sketch sketch1 = new Sketch("First", "address1", sketchDeck1);
		sketchRepo.save(sketch1);
		
		Sketch sketch2 = new Sketch("Second", "address2", sketchDeck1);
		sketchRepo.save(sketch2);
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<SketchDeck> sketchResult = sketchDeckRepo.findById(id);
		sketchDeck1 = sketchResult.get();
		assertThat(sketchDeck1.getSketches(), containsInAnyOrder(sketch1,sketch2));
	}
	
	@Test
	public void canMoveASketchToADifferentSketchDeck() {
		SketchDeck sketchDeck1 = new SketchDeck("Start");
		sketchDeckRepo.save(sketchDeck1);
		
		SketchDeck sketchDeck2 = new SketchDeck("Start");
		sketchDeckRepo.save(sketchDeck2);
		
		Sketch sketch1 = new Sketch("First", "address1", sketchDeck1);
		sketchRepo.save(sketch1);
		
		sketch1.moveDeck(sketchDeck2);
		
		entityManager.flush();
		entityManager.clear();
		
		
		assertThat(sketch1.getSketchDeck(),is(sketchDeck2));
	}
	@Test
	public void shouldBeAbleToRenameASketch() {
		SketchDeck sketchDeck1 = new SketchDeck("Start");
		sketchDeckRepo.save(sketchDeck1);
		Sketch sketch1 = new Sketch("First", "address1", sketchDeck1);
		sketchRepo.save(sketch1);
		sketch1.changeName("new name");
			
		entityManager.flush();
		entityManager.clear();
		
		assertThat(sketch1.getName(),is("new name"));
		
	}
}