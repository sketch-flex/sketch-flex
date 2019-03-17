package org.wecancodeit.sketchflex.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.sketchflex.models.Sketch;

public interface SketchRepository extends CrudRepository<Sketch, Long> {

}
