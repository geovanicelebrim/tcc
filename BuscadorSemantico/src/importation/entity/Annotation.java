package importation.entity;

public class Annotation {
	private String IDofNote, note;
	
	public Annotation(String IDofNote, String note) {
		this.IDofNote = IDofNote;
		this.note = note;
	}
	
	public String getIDofNote() {
		return this.IDofNote;
	}
	
	public String getNote() {
		return this.note;
	}
}
