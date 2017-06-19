package management.addition;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DAO.Neo4j;
import exception.DatabaseConnectionException;
import management.entity.Annotation;
import management.entity.Entity;
import management.entity.Relation;

public class Importation {

	public static void importOf(String path) throws IOException, DatabaseConnectionException, Exception {
		
//		ArrayList<String> filesToExtract =  getFilesToEntityExtract(path);
//		
//		if (filesToExtract.size() > 0) {
//			// TODO construir métodos responsáveis por realizar a extração automática
//			throw new Exception("A importação não será realizada pois foi adicionado arquivos para serem extraídos automaticamente. Esta funcionalidade ainda está em fase de construção.");
//		}
		// Obtém os arquivos .ann
		File files[] = DAO.File.listFilesOfType(path + "ann/", ".ann");
		int countFile = 1;
		for (File file : files) {
			ArrayList<String> fileLines = DAO.File.readLinesFile(path + "ann/" + file.getName());
			ArrayList<String> metadataLines = DAO.File
					.readLinesFile(path + "meta/" + file.getName().replace(".ann", ".meta"));

			ArrayList<Entity> entities = new ArrayList<>();
			ArrayList<Relation> relations = new ArrayList<>();
			ArrayList<Annotation> annotarions = new ArrayList<>();

			// Le as anotações do arquivo e as armazena em ArrayLists
			for (int i = 0; i < fileLines.size(); i++) {
				String tokensOfAnnotation[] = fileLines.get(i).replace("\n", "").split("\t");

				if (tokensOfAnnotation[0].contains("T")) {

					String ID, type, position, slice;
					ID = tokensOfAnnotation[0];
					type = tokensOfAnnotation[1].split(" ")[0];
					position = tokensOfAnnotation[1].split(" ")[1] + ", " + tokensOfAnnotation[1].split(" ")[2];
					slice = tokensOfAnnotation[2].replaceAll("\"", "\\\\\"");

					entities.add(new Entity(type, slice, ID, 1, 0, position));

				} else if (tokensOfAnnotation[0].contains("R")) {
					String ID, typeRelation, beginRelation, endRelation;

					ID = tokensOfAnnotation[0];
					typeRelation = tokensOfAnnotation[1].split(" ")[0];
					beginRelation = tokensOfAnnotation[1].split(" ")[1].split(":")[1];
					endRelation = tokensOfAnnotation[1].split(" ")[2].split(":")[1];

					relations.add(new Relation(ID, typeRelation, beginRelation, endRelation));

				} else if (tokensOfAnnotation[0].contains("#")) {
					String IDofNote, note;

					IDofNote = tokensOfAnnotation[1].split(" ")[1];
					note = tokensOfAnnotation[2];

					annotarions.add(new Annotation(IDofNote, note));
				}
			}

			// Constroi as queryes das entidades
			HashMap<String, String> entity_querys = new HashMap<String, String>();

			for (int i = 0; i < entities.size(); i++) {

				if (entity_querys.containsKey(entities.get(i).getSlice())) {
					Integer citations = 1 + Integer.parseInt(
							entity_querys.get(entities.get(i).getSlice()).split("numeroCitacoes:")[1].split(",")[0]);
					String new_ID = entity_querys.get(entities.get(i).getSlice()).split("id:\"")[1].split("\",")[0]
							+ "; " + entities.get(i).getID();
					String new_position = entity_querys.get(entities.get(i).getSlice()).split("posicao:\"")[1]
							.split("\",")[0] + "; " + entities.get(i).getPositions();
					String new_query = "create(:" + entities.get(i).getType() + "{id:\"" + new_ID + "\", posicao:\""
							+ new_position + "\", trecho:\"" + entities.get(i).getSlice() + "\", numeroCitacoes:"
							+ citations + ", numeroRelacoes:1})";

					entity_querys.remove(entities.get(i).getSlice());
					entity_querys.put(entities.get(i).getSlice(), new_query);

				} else {
					String query;
					query = "create(:" + entities.get(i).getType() + "{id:\"" + entities.get(i).getID()
							+ "\", posicao:\"" + entities.get(i).getPositions() + "\", trecho:\""
							+ entities.get(i).getSlice() + "\", numeroCitacoes:1, numeroRelacoes:1})";
					entity_querys.put(entities.get(i).getSlice(), query);
				}

			}

			// Constroi as queryes das relacoes
			HashMap<String, String> relation_querys = new HashMap<String, String>();

			for (int i = 0; i < relations.size(); i++) {
				String slice_entity1 = "", slice_entity2 = "", query;

				for (int j = 0; j < entities.size(); j++) {
					if (relations.get(i).getBeginRelation().equals(entities.get(j).getID())) {
						slice_entity1 = entities.get(j).getSlice();
					}
					if (relations.get(i).getEndRelation().equals(entities.get(j).getID())) {
						slice_entity2 = entities.get(j).getSlice();
					}
				}

				query = "match (e1) where not (e1)-[]-(:Arquivo) and e1.trecho = \"" + slice_entity1
						+ "\" match (e2) where not (e2)-[]-(:Arquivo) and e2.trecho = \"" + slice_entity2
						+ "\" create (e1)-[:" + relations.get(i).getTypeRelation() + "{id:\"" + relations.get(i).getID()
						+ "\", nota:\"\"}]->(e2)";
				relation_querys.put(query, query);

				// Atualiza o numero de relações das entidades
				entity_querys.get(slice_entity1).split("numeroRelacoes:")[0].split("}");
				Integer r1 = 1
						+ Integer.parseInt(entity_querys.get(slice_entity1).split("numeroRelacoes:")[1].split("}")[0]);
				Integer r2 = 1
						+ Integer.parseInt(entity_querys.get(slice_entity2).split("numeroRelacoes:")[1].split("}")[0]);
				String q1 = entity_querys.get(slice_entity1).split("numeroRelacoes:")[0] + "numeroRelacoes:" + r1
						+ "})";
				String q2 = entity_querys.get(slice_entity2).split("numeroRelacoes:")[0] + "numeroRelacoes:" + r2
						+ "})";

				entity_querys.remove(slice_entity1);
				entity_querys.remove(slice_entity2);

				entity_querys.put(slice_entity1, q1);
				entity_querys.put(slice_entity2, q2);
			}

			// Constroi as queryes das anotaçoes
			HashMap<String, String> annotation_querys = new HashMap<String, String>();

			for (int i = 0; i < annotarions.size(); i++) {
				if (annotarions.get(i).getIDofNote().contains("T")) {
					for (int j = 0; j < entities.size(); j++) {
						if (annotarions.get(i).getIDofNote().equals(entities.get(j).getID())) {
							String query = "match (a) where a.trecho = \"" + entities.get(j).getSlice()
									+ "\" set a.nota = \"" + annotarions.get(i).getNote() + "\"";
							annotation_querys.put(query, query);
						}
					}
				} else {
					for (int j = 0; j < relations.size(); j++) {
						if (annotarions.get(i).getIDofNote().equals(relations.get(j).getID())) {
							String query = "match (a)-[r]-(b) where r.id = \"" + relations.get(j).getID()
									+ "\" set a.nota = \"" + annotarions.get(i).getNote() + "\"";
							annotation_querys.put(query, query);
						}
					}
				}
			}

			// Cria a entidade Arquivo
			ArrayList<String> document_querys = new ArrayList<String>();
			String query = "create(:Arquivo {tipo:\"Arquivo\", nome:\"" + metadataLines.get(0).split("\t")[1]
					+ "\", titulo:\"" + metadataLines.get(1).split("\t")[1] + "\", autor:\""
					+ metadataLines.get(2).split("\t")[1] + "\", ano:\"" + metadataLines.get(3).split("\t")[1]
					+ "\", fonte:\"" + metadataLines.get(4).split("\t")[1] + "\", caminho:\""
					+ metadataLines.get(5).split("\t")[1] + "\", dataModificacao:\""
					+ metadataLines.get(6).split("\t")[1] + "\"})";
			document_querys.add(query);
			query = "match (d:Arquivo) where d.nome = \"" + metadataLines.get(0).split("\t")[1]
					+ "\" match (n) where not (n)-[]-(:Arquivo) and not Labels(n) = \"Arquivo\" create (n)-[:Relacao]->(d)";
			document_querys.add(query);

			// Serializa os dados no banco
			System.out.println("File " + countFile + " of " + files.length);

			Neo4j neo4j;
			int countEntity = 1, countRelation = 1, countAnnotation = 1;

			for (Map.Entry<String, String> pair : entity_querys.entrySet()) {
				System.out.println("\tEntity " + countEntity + " of " + entity_querys.size());
				countEntity++;
				neo4j = new Neo4j();
				neo4j.getSession().run(pair.getValue());
				neo4j.disconnect();
			}

			for (Map.Entry<String, String> pair : relation_querys.entrySet()) {
				System.out.println("\tRelation " + countRelation + " of " + relation_querys.size());
				countRelation++;
				neo4j = new Neo4j();
				neo4j.getSession().run(pair.getValue());
				neo4j.disconnect();
			}

			for (Map.Entry<String, String> pair : annotation_querys.entrySet()) {
				System.out.println("\tAnnotation " + countAnnotation + " of " + annotation_querys.size());
				countAnnotation++;
				neo4j = new Neo4j();
				neo4j.getSession().run(pair.getValue());
				neo4j.disconnect();
			}

			for (int i = 0; i < document_querys.size(); i++) {
				neo4j = new Neo4j();
				neo4j.getSession().run(document_querys.get(i));
				neo4j.disconnect();
			}

			moveImportedFile(path + "ann/", file.getName());
			moveImportedFile(path + "meta/", file.getName().replace(".ann", ".meta"));

			countFile++;
		}

	}

	private static ArrayList<String> getFilesToEntityExtract(String path) {
		File metaFiles[] = DAO.File.listFilesOfType(path + "meta/", ".meta");
		File annotationFiles[] = DAO.File.listFilesOfType(path + "ann/", ".ann");
		ArrayList<String> filesToExtract = new ArrayList<>();
		
		if (metaFiles.length > 0) {
			for (int i = 0; i < metaFiles.length; i++) {
				if (annotationFiles.length > 0) {
					for (int j = 0; j < annotationFiles.length; j++) {
						if (!metaFiles[i].getName().replaceAll(".meta", "").equals(annotationFiles[j].getName().replaceAll(".ann", ""))) {
							filesToExtract.add(metaFiles[i].getName().replaceAll(".meta", ".txt"));
						}
					}
				} else {
					filesToExtract.add(metaFiles[i].getName().replaceAll(".meta", ".txt"));
				}
			}
		}
		
		return filesToExtract;
	}
	
	private static void moveImportedFile(String path, String fileName) throws IOException {
		Path newDirectory = FileSystems.getDefault().getPath(path + "imported");
		Path source = FileSystems.getDefault().getPath(path + fileName);
		Path target = FileSystems.getDefault().getPath(path + "/imported/" + fileName);

		if (!Files.exists(newDirectory)) {
			new File(newDirectory.toString()).mkdirs();
		}

		Files.move(source, target, REPLACE_EXISTING);
	}
}
