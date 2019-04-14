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
		SketchDeck plantBulb = new SketchDeck("Plant Bulbs");
		sketchDeckRepo.save(plantBulb);
		
		Sketch choose = new Sketch("Prepare Planting","/populator/ChoiceBulbs.png",plantBulb, "Pick!");
		sketchRepo.save(choose);
		Sketch dig = new Sketch("Dig","/populator/Dig.png",plantBulb, "Dig a hole");
		sketchRepo.save(dig);
		Comment step1 = new Comment ("Step 1", choose);
		commentRepo.save(step1);
		Comment commentChoose = new Comment ("Choose a bulb.", choose);
		commentRepo.save(commentChoose);
		Sketch choice = new Sketch("Choose Bulb","/populator/TulipBulb.png",plantBulb, "Chosen: Tulip");
		sketchRepo.save(choice);
		Sketch plant = new Sketch("Plant","/populator/PlantBulb.png",plantBulb, "Plant the Tulip bulb");
		sketchRepo.save(plant);
		Sketch water = new Sketch("Water","/populator/Water.png",plantBulb, "Water");
		sketchRepo.save(water);
		Sketch gowing1 = new Sketch("Wait","/populator/TulipGrowing.png",plantBulb, "wait...");
		sketchRepo.save(gowing1);
		Sketch gowing2 = new Sketch("Wait 2","/populator/TulipBudding.png",plantBulb, "wait more...");
		sketchRepo.save(gowing2);
		Sketch gowing3 = new Sketch("Wait 3","/populator/TulipBuddingWithePetals.png",plantBulb, "Almost there...");
		sketchRepo.save(gowing3);
		Sketch tulip = new Sketch("Tulip","/populator/TulipBloom.png",plantBulb, "Success!");
		sketchRepo.save(tulip);
		
		
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
		
		SketchDeck beverages =new SketchDeck("Beverages");
		sketchDeckRepo.save(beverages);
		
		Sketch coffee = new Sketch("Coffee", "/populator/coffee.jpg",beverages);
		sketchRepo.save(coffee);
		
		Sketch tea = new Sketch("Tea", "/populator/tea.jpg",beverages);
		sketchRepo.save(tea);
		
	}

}