package org.wecancodeit.sketchflex.controllers;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wecancodeit.sketchflex.exceptions.StorageException;
import org.wecancodeit.sketchflex.exceptions.StorageFileNotFoundException;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;
import org.wecancodeit.sketchflex.storage.StorageService;

@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	private SketchRepository sketchRepo;

	@Autowired
	private SketchDeckRepository sketchDeckRepo;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) throws IOException, StorageException {

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));
		model.addAttribute("sketchDecks", sketchDeckRepo.findAll());

		return "sketch-upload-template";
	}

	@GetMapping("/saved-sketches/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws StorageFileNotFoundException {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			String sketchDeckName, String name) throws StorageException {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		String sketchName = name;
		String imageLocation = "/saved-sketches/" + file.getOriginalFilename();
		SketchDeck sketchDeck = sketchDeckRepo.findByNameContainingIgnoreCase(sketchDeckName);
		if (sketchDeck == null) {
			if(sketchDeckName.equals("")) {
				sketchDeckName = "none";
			}
			sketchDeck = new SketchDeck(sketchDeckName);
			sketchDeckRepo.save(sketchDeck);
		}
		Sketch sketchAdded = new Sketch(sketchName, imageLocation, sketchDeck);
		sketchRepo.save(sketchAdded);

		return "redirect:/upload";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}