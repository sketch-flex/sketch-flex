package org.wecancodeit.sketchflex;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

@Component
public class SketchFlexPopulator implements CommandLineRunner{

	@Resource
	private SketchRepository sketchRepo;
	
	@Resource
	private SketchDeckRepository sketchDeckRepo;
	
	@Override
	public void run(String... args) throws Exception {
		SketchDeck dinosaurs =new SketchDeck("Dinosaurs");
		sketchDeckRepo.save(dinosaurs);
		
		Sketch tRex = new Sketch("T-Rex","/populator/t-rex.jpg",dinosaurs);
		sketchRepo.save(tRex);
		
		Sketch tric = new Sketch("Triceratops","/populator/triceratops.gif",dinosaurs);
		sketchRepo.save(tric);
		
		Sketch raptor = new Sketch("Velociraptor","/populator/raptor.jpg",dinosaurs);
		sketchRepo.save(raptor);
		
		Sketch tRex2 = new Sketch("T-Rex kids drawing","/populator/t-rex2.png",dinosaurs);
		sketchRepo.save(tRex2);
	}

}
