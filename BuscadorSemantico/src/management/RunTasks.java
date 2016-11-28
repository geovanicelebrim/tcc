package management;

import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import control.Management;

public class RunTasks {
	private static RunTasks instance;
	private static ArrayList<Date> sheduleIndex = new ArrayList<>();
	private static ArrayList<Date> sheduleImport = new ArrayList<>();
	private static boolean permission = true;
	
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
					for (Date shedule : sheduleIndex) {
						if (now.after(shedule)) {
							
							try {
								Management.indexerData(OpenMode.CREATE);
								Management.buildDictionary();
							} catch (Exception e) {
								System.out.println("Ocorreu uma falha na indexação. Classe: " + this.getClass().getName());
							}
							
							sheduleIndex.remove(shedule);
							System.out.println("Tarefa agendada concluída. Removendo -> " + shedule);
							break;
						}
					}
					
					//Verifica se existe alguma tarefa de importação agendada. Essa importação não impede
					//que o usuário realize pesquisas simultaneamente.
					for (Date shedule : sheduleImport) {
						if (now.after(shedule)) {
							
							try {
								Management.importAnn();
							} catch (Exception e) {
								System.out.println("Ocorreu uma falha na importação. Classe: " + this.getClass().getName());
							}
							
							sheduleImport.remove(shedule);
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
	
	public void addIndexShedule(Date date) {
		
		while(!permission) {};
		if(!sheduleIndex.contains(date)) { 
			sheduleIndex.add(date);
			sheduleIndex.sort((d0, d1) -> d1.compareTo(d0));
		}
	}

	public void addImportShedule(Date date) {
		
		while(!permission) {};
		if(!sheduleImport.contains(date)) { 
			sheduleImport.add(date);
			sheduleImport.sort((d0, d1) -> d1.compareTo(d0));
		}
	}
	
	public int getIndexSheduleSize() {
		
		while(!permission) {};
		
		return sheduleIndex.size();
	}
	
	public int getImportSheduleSize() {
		
		while(!permission) {};
		
		return sheduleImport.size();
	}
}
