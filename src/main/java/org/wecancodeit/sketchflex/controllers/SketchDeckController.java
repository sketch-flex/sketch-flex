package org.wecancodeit.sketchflex.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;


@Controller
public class SketchDeckController {
	
	@Resource
	SketchDeckRepository sketchDeckRepo;

	
	@RequestMapping("/sketchdeck")
	public String findOneSketchDeck(@RequestParam(value = "id")Long id, Model model) throws SketchDeckNotFoundException{
	        Optional<SketchDeck> sketchDeck = sketchDeckRepo.findById(id);
			
			if(sketchDeck.isPresent()){
				model.addAttribute("single-sketchdeck", sketchDeck.get());
				// model.addAttribute("all-sketches",sketchDeck.get().getSketches());
				
				return "single-sketchdeck-template";
			}
			throw new SketchDeckNotFoundException();
	}
    
	@RequestMapping("/sketchdecks")
	public String findAllSketches(Model model) {
		
		model.addAttribute("all-sketchdecks",sketchDeckRepo.findAll());
		
		return "all-sketchdecks-template";	
	}

}
