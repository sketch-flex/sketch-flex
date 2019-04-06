package org.wecancodeit.sketchflex;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wecancodeit.sketchflex.models.Comment;
import org.wecancodeit.sketchflex.models.Sketch;
import org.wecancodeit.sketchflex.models.SketchDeck;
import org.wecancodeit.sketchflex.repositories.CommentRepository;
import org.wecancodeit.sketchflex.repositories.SketchDeckRepository;
import org.wecancodeit.sketchflex.repositories.SketchRepository;

@Component
public class SketchFlexPopulator implements CommandLineRunner{

	@Resource
	private SketchRepository sketchRepo;
	
	@Resource
	private SketchDeckRepository sketchDeckRepo;
	
	@Resource
	private CommentRepository commentRepo;
	
	@Override
	public void run(String... args) throws Exception {
		SketchDeck dinosaurs =new SketchDeck("Dinosaurs");
		sketchDeckRepo.save(dinosaurs);
		
		Sketch tRex = new Sketch("T-Rex","/populator/t-rex.jpg",dinosaurs, "Rawr!");
		sketchRepo.save(tRex);
		Comment commentTRex1 = new Comment ("Picture of a t-rex!", tRex);
		commentRepo.save(commentTRex1);
		Comment commentTRex2 = new Comment ("Try and delete this note!!!!!", tRex);
		commentRepo.save(commentTRex2);
		
		Sketch tric = new Sketch("Triceratops","/populator/triceratops.gif",dinosaurs);
		sketchRepo.save(tric);
		
		Sketch raptor = new Sketch("Velociraptor","/populator/raptor.jpg",dinosaurs);
		sketchRepo.save(raptor);
		
		Sketch tRex2 = new Sketch("T-Rex kids drawing","/populator/t-rex2.png",dinosaurs,"silly sketch works!!");
		sketchRepo.save(tRex2);
		
		SketchDeck beverages =new SketchDeck("Beverages", "A list of beverages");
		sketchDeckRepo.save(beverages);
		
		Sketch coffee = new Sketch("Coffee", "/populator/coffee.jpg",beverages);
		sketchRepo.save(coffee);
		
		Sketch tea = new Sketch("Tea", "/populator/tea.jpg",beverages);
		sketchRepo.save(tea);
		
	}

}
