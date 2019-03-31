package org.wecancodeit.sketchflex.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.storage.StorageService;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class SketchDeckDescriptionJPATest {

	@MockBean
	private StorageService storageService;

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private SketchDeckRepository sketchDeckRepo;

	@Test
	public void shouldBeAbleToSaveADescriptionToASketchDeck() {
		SketchDeck deck1 = new SketchDeck("deck1", "description");
		sketchDeckRepo.save(deck1);
		Long id = deck1.getId();

		entityManager.flush();
		entityManager.clear();

		String foundDescription = sketchDeckRepo.findById(id).get().getDescription();
		assertThat(foundDescription, is("description"));
	}

	@Test
	public void shouldBeAbleToAddADescriptionToASketchDeckThatDidNotHaveOneBefore() {
		SketchDeck deck1 = new SketchDeck("deck1");
		sketchDeckRepo.save(deck1);
		Long id = deck1.getId();
		
		deck1.setDescription("lols");
		sketchDeckRepo.save(deck1);

		entityManager.flush();
		entityManager.clear();

		String foundDescription = sketchDeckRepo.findById(id).get().getDescription();
		assertThat(foundDescription, is("lols"));
	}

	@Test
	public void shouldBeAbleToUpdateDescription() {
		SketchDeck deck1 = new SketchDeck("deck1", "rawr!");
		sketchDeckRepo.save(deck1);
		Long id = deck1.getId();
		
		deck1.setDescription("here");
		sketchDeckRepo.save(deck1);
		
		entityManager.flush();
		entityManager.clear();
		
		String foundDescription = sketchDeckRepo.findById(id).get().getDescription();
		assertThat(foundDescription, is("here"));
	}
	
}
