package org.wecancodeit.sketchflex.controllers;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.sketchflex.exceptions.SketchNotFoundException;
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
		model.addAttribute("sketchDecks", sketchDeckRepo.findAll());
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
		if (sketchDeckName != null) {
			SketchDeck sketchDeck = sketchDeckRepo.findByNameContainingIgnoreCase(sketchDeckName);
			if (sketchDeck == null) {
				sketchDeck = sketchDeckRepo.save(new SketchDeck(sketchDeckName));
			}
			Sketch sketch = sketchRepo.findFirstByName(name);
			if (sketch == null || name.equals("")) {
				sketchRepo.save(new Sketch(name, lob, sketchDeck));
			}
			return "redirect:/sketchdeck?id=" + sketchDeck.getId();
		} else {
			return "redirect:/draw";
		}
	}

	@RequestMapping("/draw")
	public String goToDraw(Model model) {
		model.addAttribute("sketchDecks", sketchDeckRepo.findAll());
		return "sketch-draw-template";
	}

	@RequestMapping(path = "/{id}/note/{note}", method = RequestMethod.POST)
	public String updateNote(@PathVariable("note") String note, @PathVariable("id") Long id, Model model) {

		Sketch sketch = sketchRepo.findById(id).get();
		sketch.setNote(note);
		sketchRepo.save(sketch);
		List<Sketch> list = sketchRepo.findAllBySketchDeck(sketch.getSketchDeck());
		Gson gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String jsonList = gsonBuilder.toJson(list);
		model.addAttribute("JSONsketches", jsonList);
		return "partials/sketch-note-updated";
	}
	
	@RequestMapping(path= "/next/{sketchId}", method = RequestMethod.POST)
	public String nextSketch(@PathVariable("sketchId") Long id, Model model) {
		System.out.println(id);
		Sketch sketch = sketchRepo.findById(id).get();
		SketchDeck sketchDeck = sketch.getSketchDeck();
		List<Sketch> sketches = sketchDeck.getSketches();
		int index = sketches.indexOf(sketch);
		if (index < sketches.size()-1) {
			index ++;
			sketch = sketches.get(index);
			model.addAttribute("sketch", sketch);
		}
		return "partials/sketch-note-updated";
	}
	
	@RequestMapping(path= "/previous/{sketchId}", method = RequestMethod.POST)
	public String previousSketch(@PathVariable("sketchId") Long id, Model model) {
		System.out.println(id);
		Sketch sketch = sketchRepo.findById(id).get();
		SketchDeck sketchDeck = sketch.getSketchDeck();
		List<Sketch> sketches = sketchDeck.getSketches();
		int index = sketches.indexOf(sketch);
		if (index > 0) {
			index --;
			sketch = sketches.get(index);
			model.addAttribute("sketch", sketch);
		}
		return "partials/sketch-note-updated";
	}
	
	

}
