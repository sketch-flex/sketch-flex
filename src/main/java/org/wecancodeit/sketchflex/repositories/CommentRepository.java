package org.wecancodeit.sketchflex.repositories;

import org.springframework.data.repository.CrudRepository;
import org.wecancodeit.sketchflex.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	Comment findByContent(String content);

}
