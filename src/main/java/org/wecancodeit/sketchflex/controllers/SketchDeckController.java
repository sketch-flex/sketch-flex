package org.wecancodeit.sketchflex.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.sketchflex.exceptions.SketchDeckNotFoundException;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;


@Controller
public class SketchDeckController {
	
	@Resource
	SketchDeckRepository sketchDeckRepo;
	
	@Resource
	SketchRepository sketchRepo;
	
	@RequestMapping("/sketchdeck")
	public String findOneSketchDeck(@RequestParam(value = "id")Long id, Model model) throws SketchDeckNotFoundException{
	        Optional<SketchDeck> sketchDeck = sketchDeckRepo.findById(id);
			
			if(sketchDeck.isPresent()){
				model.addAttribute("sketchdeck", sketchDeck.get());
				model.addAttribute("sketches",sketchDeck.get().getSketches());
				
				return "single-sketchdeck-template";
			}
			throw new SketchDeckNotFoundException();
	}
    
	@RequestMapping("/sketchdecks")
	public String findAllSketches(Model model) {
		
		model.addAttribute("sketchDecks",sketchDeckRepo.findAll());
		
		return "all-sketchdecks-template";	
	}
	
	@RequestMapping("/home")
	public String displayHome() {
		return "index";
	}
	
	@RequestMapping(path = "/{idOne}/reorder/{idTwo}", method = RequestMethod.POST)
	public String reorderSketches(@PathVariable("idOne") Long idOne, @PathVariable("idTwo") Long idTwo, Model model) {
		Sketch sketchOne = sketchRepo.findById(idOne).get();
		Sketch sketchTwo = sketchRepo.findById(idTwo).get();
		Long sketchOneOrder = sketchOne.getOrder();
		Long sketchTwoOrder = sketchTwo.getOrder();
		sketchOne.setOrder(sketchTwoOrder);
		sketchTwo.setOrder(sketchOneOrder);
		sketchRepo.save(sketchOne);
		sketchRepo.save(sketchTwo);
		SketchDeck sketchDeck = sketchOne.getSketchDeck();
		model.addAttribute("sketches",sketchDeck.getSketches());
		return "partials/sketchDeck-order-changed";
	}

}
