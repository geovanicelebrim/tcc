package management.entity;

import java.util.Date;

public class Task {

	private String type;
	private Date creation;
	private Date scheduled;
	private boolean executed;
	
	public Task(String type, Date scheduled) {
		Date now = new Date();
		
		this.type = type;
		this.creation = now;
		this.scheduled = scheduled;
		this.executed = false;
	}
	
	public String getType() {
		return this.type;
	}
	
	public Date getCreation() {
		return this.creation;
	}
	
	public Date getScheduled() {
		return this.scheduled;
	}
	
	public boolean getExecuted() {
		return this.executed;
	}
	
	public void execute() {
		this.executed = true;
	}
}
