package org.wecancodeit.sketchflex.controllers;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

@Controller
public class SketchController {

	@Resource
	private SketchRepository sketchRepo;

	@RequestMapping("/all-sketches")
	public String findAllSketches(Model model) {
		model.addAttribute("sketches", sketchRepo.findAll());
		return "all-sketches";

	}

	@RequestMapping("/sketch")
	public String findOneSketch(@RequestParam(value = "id") long id, Model model) throws SketchNotFoundException {
		Optional<Sketch> sketch = sketchRepo.findById(id);
		if (sketch.isPresent()) {
			model.addAttribute("sketch", sketch.get());
			return "sketch";
		}
		throw new SketchNotFoundException();
	}

	@RequestMapping("/add-sketch")
	public String addSketch(String name, String address, SketchDeck sketchDeck) {
		if (sketchDeck != null) {
			Sketch sketch = sketchRepo.findByName(name);
			if(sketch == null) {
				sketchRepo.save(new Sketch(name, address, sketchDeck));
			}
		}
		return "redirect:/all-sketches";
	}
	
	

}
