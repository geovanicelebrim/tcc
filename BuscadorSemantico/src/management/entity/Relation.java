package management.entity;

public class Relation {
	private String ID, typeRelation, beginRelation, endRelation;
	
	public Relation(String ID, String typeRelation, String beginRelation, String endRelation) {
		this.ID = ID;
		this.typeRelation = typeRelation;
		this.beginRelation = beginRelation;
		this.endRelation = endRelation;
	}

	public String getID() {
		return this.ID;
	}

	public String getTypeRelation() {
		return this.typeRelation;
	}

	public String getBeginRelation() {
		return this.beginRelation;
	}

	public String getEndRelation() {
		return this.endRelation;
	}
	
}
