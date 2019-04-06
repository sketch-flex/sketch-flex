package org.wecancodeit.sketchflex.controllers;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.Comment;
import org.wecancodeit.sketchflex.repositories.CommentRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

public class CommentControllerTest {

	@InjectMocks
	private CommentController underTest;
	
	@Mock
	private SketchRepository sketchRepo;
	
	@Mock
	private CommentRepository commentRepo;
	
	@Mock
	private Comment comment;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddAdditionalCommentToSketch() {
		String sketchName = "sketch name";
		Sketch newSketch = sketchRepo.findByName(sketchName);
		String commentContent = "new comment";
		underTest.addComment(commentContent, sketchName);
		Comment newComment = new Comment(commentContent, newSketch);
		when(commentRepo.save(newComment)).thenReturn(newComment);
	}
	
}
