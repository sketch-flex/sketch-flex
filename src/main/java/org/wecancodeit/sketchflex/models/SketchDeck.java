package org.wecancodeit.sketchflex.models;

import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
public class SketchDeck {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Lob
	private String description;

	@OneToMany(mappedBy = "sketchDeck")
	private List<Sketch> sketches;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<Sketch> getSketches() {
		sketches.sort(Comparator.comparing(sketch->sketch.getOrder()));
		return sketches;
	}

	public Sketch getSketch() {
		return sketches.get(0);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected SketchDeck() {
		// WHYYYYYYYYYY?
	}

	public SketchDeck(String name) {
		this.name = name;
	}

	public SketchDeck(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void add(Sketch sketch) {
		sketches.add(sketch);

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
		SketchDeck other = (SketchDeck) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
