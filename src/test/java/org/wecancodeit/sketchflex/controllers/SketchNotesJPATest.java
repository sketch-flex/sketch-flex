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
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;
import org.wecancodeit.sketchflex.storage.StorageService;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class SketchNotesJPATest {

	@MockBean
	private StorageService storageService;

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private SketchDeckRepository sketchDeckRepo;

	@Resource
	private SketchRepository sketchRepo;

	@Test
	public void shouldBeAbleToSaveANoteToASketch() {
		SketchDeck deck1 = new SketchDeck("deck1");
		Sketch sketch1 = new Sketch("name", "imageLocation", deck1, "note");

		sketchDeckRepo.save(deck1);
		sketchRepo.save(sketch1);
		Long id = sketch1.getId();

		entityManager.flush();
		entityManager.clear();

		String foundNote = sketchRepo.findById(id).get().getNote();
		assertThat(foundNote, is("note"));
	}

	@Test
	public void shouldBeAbleToAddANoteToASketchThatDidNotHaveOneBefore() {
		SketchDeck deck1 = new SketchDeck("deck1");
		Sketch sketch1 = new Sketch("name", "imageLocation", deck1);

		sketchDeckRepo.save(deck1);
		sketchRepo.save(sketch1);
		Long id = sketch1.getId();
		
		sketch1.setNote("note4");
		sketchRepo.save(sketch1);
		
		entityManager.flush();
		entityManager.clear();
		
		String foundnote = sketchRepo.findById(id).get().getNote();
		assertThat(foundnote, is("note4"));
	}
	
	@Test
	public void shouldBeAbleToUpdateNote() {
		SketchDeck deck1 = new SketchDeck("deck1");
		Sketch sketch1 = new Sketch("name", "imageLocation", deck1, "rawr!");

		sketchDeckRepo.save(deck1);
		sketchRepo.save(sketch1);
		Long id = sketch1.getId();
		
		sketch1.setNote("note4");
		sketchRepo.save(sketch1);
		
		entityManager.flush();
		entityManager.clear();
		
		String foundnote = sketchRepo.findById(id).get().getNote();
		assertThat(foundnote, is("note4"));
	}
	
}
