package org.wecancodeit.sketchflex.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;


@Controller
public class SketchDeckController {
	
	@Resource
	SketchDeckRepository sketchDeckRepo;

	public String findOneSketchDeck(Long id, Model model) throws SketchDeckNotFoundException{
	        Optional<SketchDeck> sketchDeck = sketchDeckRepo.findById(id);
			
			if(sketchDeck.isPresent()){
				model.addAttribute("single-sketchdeck", sketchDeck.get());
				
				return "";
			}
			throw new SketchDeckNotFoundException();
	}

	public String findAllSketches(Model model) {
		
		model.addAttribute("all-sketchdecks",sketchDeckRepo.findAll());
		
		return "";	
	}

}
