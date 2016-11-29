package management;

import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import control.ManagementAddNewFile;

public class RunTasks {
	private static RunTasks instance;
	private static ArrayList<Date> scheduleIndex = new ArrayList<>();
	private static ArrayList<Date> scheduleImport = new ArrayList<>();
	private static boolean permission = true;
	private static boolean permissionForAdd = true;
	
	private RunTasks () {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					Date now = new Date();
					permission = false;
					//Verifica se existe alguma tarefa de indexação agendada. Essa indexação é dada de forma completa, ou seja,
					//O indice é recriado, de forma que otimize o tamanho ocupado em disco. Lembrando que a ação não impede
					//que o usuário realize pesquisas simultaneamente. Os indices são replicados até estarem completamente 
					//construídos.
					for (Date shedule : scheduleIndex) {
						if (now.after(shedule)) {
							
							try {
								ManagementAddNewFile.indexerData(OpenMode.CREATE);
								ManagementAddNewFile.buildDictionary();
							} catch (Exception e) {
								System.out.println("Ocorreu uma falha na indexação. Classe: " + this.getClass().getName());
							}
							
							scheduleIndex.remove(shedule);
							System.out.println("Tarefa agendada concluída. Removendo -> " + shedule);
							break;
						}
					}
					
					//Verifica se existe alguma tarefa de importação agendada. Essa importação não impede
					//que o usuário realize pesquisas simultaneamente.
					for (Date shedule : scheduleImport) {
						if (now.after(shedule)) {
							
							try {
								ManagementAddNewFile.importAnn();
							} catch (Exception e) {
								System.out.println("Ocorreu uma falha na importação. Classe: " + this.getClass().getName());
							}
							
							scheduleImport.remove(shedule);
							System.out.println("Tarefa agendada concluída. Removendo -> " + shedule);
							break;
						}
					}
					permission = true;
					
					try {
						Thread.sleep(15000);
					} catch (Exception e) {
						// TODO: handle exception
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
	
	public void addIndexSchedule(Date date) {
		
		while(!permission && !permissionForAdd) {};
		permissionForAdd = false;
		if(!scheduleIndex.contains(date)) { 
			scheduleIndex.add(date);
			scheduleIndex.sort((d0, d1) -> d1.compareTo(d0));
		}
		permissionForAdd = true;
	}

	public void addImportSchedule(Date date) {
		
		while(!permission && !permissionForAdd) {};
		permissionForAdd = false;
		if(!scheduleImport.contains(date)) { 
			scheduleImport.add(date);
			scheduleImport.sort((d0, d1) -> d1.compareTo(d0));
		}
		permissionForAdd = true;
	}
	
	public int getIndexScheduleSize() {
		
		while(!permission) {};
		
		return scheduleIndex.size();
	}
	
	public int getImportScheduleSize() {
		
		while(!permission) {};
		
		return scheduleImport.size();
	}
}
