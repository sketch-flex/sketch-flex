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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsSketchTest {

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
	
}
