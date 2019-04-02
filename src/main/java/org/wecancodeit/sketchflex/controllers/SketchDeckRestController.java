package org.wecancodeit.sketchflex.controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;

@RestController
@RequestMapping
public class SketchDeckRestController {
	
	@Resource
	private SketchDeckRepository sketchDeckRepo;
	
	@RequestMapping("/allSketchDecks")
	public Iterable<SketchDeck> findAllSketchDecks(){
		return sketchDeckRepo.findAll();
	}
}
