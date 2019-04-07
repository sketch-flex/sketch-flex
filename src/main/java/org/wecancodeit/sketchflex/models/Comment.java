package org.wecancodeit.sketchflex.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	private Long id;
	@Lob
	private String content;
	@ManyToOne
	private Sketch sketch;
	
	public Long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public Sketch getSketch() {
		return sketch;
	}
	
	protected Comment() {
	 //Why???	
	}
	
	public Comment(String content, Sketch sketch) {
		this.content = content;
		this.sketch = sketch;
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
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
