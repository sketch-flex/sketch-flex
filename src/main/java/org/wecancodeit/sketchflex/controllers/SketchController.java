package org.wecancodeit.sketchflex.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class SketchController {

	@Resource
	private SketchRepository sketchRepo;

	@Resource
	private SketchDeckRepository sketchDeckRepo;

	@RequestMapping("/sketches")
	public String findAllSketches(Model model) {
		model.addAttribute("sketches", sketchRepo.findAll());
		return "all-sketches-template";

	}

	@RequestMapping("/sketch")
	public String findOneSketch(@RequestParam(value = "id") long id, Model model) throws SketchNotFoundException {
		Optional<Sketch> sketch = sketchRepo.findById(id);
		if (sketch.isPresent()) {
			model.addAttribute("sketch", sketch.get());
			SketchDeck sketchDeck = sketch.get().getSketchDeck();
			List<Sketch> list = sketchRepo.findAllBySketchDeck(sketchDeck);
			model.addAttribute("sketches", list);
			Gson gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			String jsonList = gsonBuilder.toJson(list);
			model.addAttribute("JSONsketches", jsonList);
			return "single-sketch-template";
		}
		throw new SketchNotFoundException();
	}

	@RequestMapping("/add-sketch")
	public String addSketch(String name, String address, SketchDeck sketchDeck) {
		if (sketchDeck != null) {
			Sketch sketch = sketchRepo.findByName(name);
			if (sketch == null) {
				sketchRepo.save(new Sketch(name, address, sketchDeck));
			}
		}
		return "redirect:/sketches";
	}

	@RequestMapping("/add-sketch-lob")
	public String addSketchLob(String name, String lob, String sketchDeckName) {
		SketchDeck sketchDeck = sketchDeckRepo.findByNameContainingIgnoreCase(sketchDeckName);
		if (sketchDeck == null) {
			sketchDeck = sketchDeckRepo.save(new SketchDeck(sketchDeckName));
		}
		Sketch sketch = sketchRepo.findByName(name);
		if (sketch == null) {
			sketchRepo.save(new Sketch(name, lob, sketchDeck));
		}

		return "redirect:/sketches";
	}

	@RequestMapping("/draw")
	public String goToDraw() {
		return "sketch-draw-template";
	}

}
