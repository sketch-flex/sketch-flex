package org.wecancodeit.sketchflex.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.Comment;
import org.wecancodeit.sketchflex.repositories.CommentRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

@Controller
public class CommentController {

	@Resource
	private SketchRepository sketchRepo;

	@Resource
	private CommentRepository commentRepo;

	@RequestMapping("/add-comment")
	public String addComment(String commentContent, String sketchName) {
		Sketch sketch = sketchRepo.findByName(sketchName);
		Comment newComment = commentRepo.findByContent(commentContent);
		Long sketchId = 1L;
		if (sketch != null) {
			sketchId = sketch.getId();
		}
		if (newComment == null) {
			newComment = new Comment(commentContent, sketch);
			commentRepo.save(newComment);
		}
		return "redirect:/sketch?id=" + sketchId;
	}

	@RequestMapping("/delete-comment")
	public String deleteSketchCommentById(Long commentId) {
		Optional<Comment> comment = commentRepo.findById(commentId);
		Long sketchId = 1L;
		if (comment.isPresent()) {
			Comment deleteComment = comment.get();
			if (deleteComment.getSketch() != null) {
				sketchId = deleteComment.getSketch().getId();
			}
			commentRepo.delete(deleteComment);
		}
		return "redirect:/sketch?id=" + sketchId;
	}
}
