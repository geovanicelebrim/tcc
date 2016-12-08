package management;

import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import control.ManagementAddNewFile;
import management.entity.Task;
import management.entity.TaskType;

public class RunTasks {
	private static RunTasks instance;
//	private static ArrayList<Date> scheduleIndex = new ArrayList<>();
//	private static ArrayList<Date> scheduleImport = new ArrayList<>();
	
	private static ArrayList<Task> tasks = new ArrayList<>();
	
	private static boolean permission = true;
	private static boolean permissionForAdd = true;
	
	private RunTasks () {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					Date now = new Date();
					permission = false;
					
					while(!permissionForAdd) {};
					
					for (Task task : tasks) {
						if (now.after(task.getScheduled())) {
							try {
								if(!task.getExecuted()) {
									if (task.getType().equals(TaskType.TYPE_INDEX)) {
										ManagementAddNewFile.indexerData(OpenMode.CREATE);
										ManagementAddNewFile.buildDictionary();
										task.execute();
										break;
									} else if (task.getType().equals(TaskType.TYPE_IMPORT)) {
										ManagementAddNewFile.importAnn();
										task.execute();
										break;
									}
								}
							} catch (Exception e) {
								util.Log.getInstance().addSystemEntry("", "Scheduled task failed.: " + e);
							}
						}
					}
					
					permission = true;
					
					try {
						Thread.sleep(15000);
					} catch (Exception e) {
					}
				}
			}
		});
		
		t.start();
	}
	
	public static synchronized RunTasks getInstance() {
		if (instance == null) {
			instance = new RunTasks();
		}
		return instance;
	}
	
	public void addTask(String type, Date scheduled) {
		Task task = new Task(type, scheduled);
		while(!permission && !permissionForAdd) {};
		permissionForAdd = false;
		if(!tasks.contains(task)) { 
			tasks.add(task);
			tasks.sort((d0, d1) -> d1.getScheduled().compareTo(d0.getScheduled()));
		}
		permissionForAdd = true;
	}
	
	public ArrayList<Task> getScheduledTask( ) {
		ArrayList<Task> ts = new ArrayList<>();
		while(!permission && !permissionForAdd) {};
		permissionForAdd = false;
		ts.addAll(tasks);
		permissionForAdd = true;
		return ts;
	}
}
