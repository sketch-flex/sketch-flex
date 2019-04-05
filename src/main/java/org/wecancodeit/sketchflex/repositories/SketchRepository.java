package org.wecancodeit.sketchflex.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;

public interface SketchRepository extends CrudRepository<Sketch, Long> {

	Sketch findByName(String name);

	List<Sketch> findAllBySketchDeck(SketchDeck sketchDeck);

	Sketch findFirstByName(String name);

}
