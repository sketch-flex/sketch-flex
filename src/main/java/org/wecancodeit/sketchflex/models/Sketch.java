package org.wecancodeit.sketchflex.models;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.google.gson.annotations.Expose;

@Entity
public class Sketch {

	@Id
	@GeneratedValue
	@Expose
	private Long id;
	@Expose
	private String name;
	@Lob
	@Expose
	private String imageLocation;
	@Lob
	@Expose
	private String note;

	@ManyToOne
	private SketchDeck sketchDeck;

	@OneToMany(mappedBy = "sketch")
	private Collection<Comment> comments;
	
	private Long deckOrder;

	public Long getId() {
		return id;
	}

	public Object getName() {
		return name;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public SketchDeck getSketchDeck() {
		return sketchDeck;
	}

	public String getNote() {
		return note;
	}
	
	public Long getOrder() {
		Long value;
		if (deckOrder == null) {
			value = id;
		}else {
			value = deckOrder;
		}
		return value;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void changeName(String newName) {
		this.name = newName;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public void setOrder(Long order) {
		this.deckOrder = order;
	}

	public void moveDeck(SketchDeck newSketchDeck) {
		this.sketchDeck = newSketchDeck;
	}

	protected Sketch() {
		// WHYYYYY????????
	}

// Do we need this? If we require that all sketches be within a sketchDeck, then this constructor isn't needed
	public Sketch(String name, String imageLocation) {
		this.name = name;
		this.setImageLocation(imageLocation);
	}

	public Sketch(String name, String imageLocation, SketchDeck sketchDeck) {
		this.name = name;
		this.setImageLocation(imageLocation);
		this.sketchDeck = sketchDeck;
	}

	public Sketch(String name, String imageLocation, SketchDeck sketchDeck, String note) {
		this.name = name;
		this.setImageLocation(imageLocation);
		this.sketchDeck = sketchDeck;
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sketch other = (Sketch) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}