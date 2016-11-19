package keyWordsSearcher;
public class Result {
	private String pathDocument;
	private String title;
	private String author;
	private Float score;
	
	private static String prefixedPath = "./lucene/data/";
	
	public Result(String file, String author, Float score) {
		this.pathDocument = prefixedPath + title;
		this.title = file.replaceAll(".txt", "");
		this.author = author;
		this.score = score;
	}
	
	public String getPathDocument() {
		return this.pathDocument;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAuthor() {
		return this.author;
	}

	public Float getScore() {
		return this.score;
	}
	
	public void setScore(Float score) {
		this.score = score;
	}	
}
