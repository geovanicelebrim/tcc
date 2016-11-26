package management.entity;
public class Entity {
	private String type;
	private String slice;
	private String ID;
	private Integer citations;
	private Integer relations;
	private String positions;

	public Entity(String type, String slice, String ID, Integer citations, Integer relations, String positions) {
		this.type = type;
		this.slice = slice;
		this.ID = ID;
		this.citations = citations;
		this.relations = relations;
		this.positions = positions;
	}

	public String getType() {
		return this.type;
	}
	
	public String getSlice() {
		return this.slice;
	}

	public String getID() {
		return this.ID;
	}

	public Integer getCitations() {
		return this.citations;
	}

	public Integer getRelations() {
		return this.relations;
	}

	public String getPositions() {
		return this.positions;
	}
	
}
