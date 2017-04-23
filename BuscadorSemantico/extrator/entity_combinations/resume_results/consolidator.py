#-*-coding:utf-8-*-

import os
import re

def write_resume(path, precision, recall, f1):
	file = open(path + "_precision", "w")
	for row in precision:
		for col in row:
			file.write(col + "\t")
		file.write("\n")
	file.close()
	
	file = open(path + "_recall", "w")
	for row in recall:
		for col in row:
			file.write(col + "\t")
		file.write("\n")
	file.close()
	
	file = open(path + "_f1", "w")
	for row in f1:
		for col in row:
			file.write(col + "\t")
		file.write("\n")
	file.close()

experiments = ["../results/naivebayes variando cutoff/",
				"../results/maxent variando iteracoes e cutoff/",
				"../results/perceptron variando iteracoes e cutoff/",
				"../results/maxent_qn variando iteracoes L1 L2 cutoff e NFU/",
				"../results/default/"]

resume_files = ["resume/resume_naivebayes_varying_cutoff",
				"resume/resume_maxent_varying_iterations",
				"resume/resume_perceptron_varying_iterations_and_cutoff",
				"resume/resume_maxent_qn_varying_iterations_L1_L2_cutoff_and_NFU",
				"resume/resume_default"]

entities_mapper = dict()

alter_entities_mapper = {"Papel": "Data.Documento.Local.Papel.Quantidade", 
						"Quantidade": "Data.Documento.Local.Papel.Quantidade", 
						"Evento": "Data.Documento.Evento.Grupo.Local.Organizacao.Papel.Pessoa.Quantidade", 
						"Documento": "Data.Documento.Grupo.Organizacao.Papel.Quantidade", 
						"Organizacao": "Data.Documento.Evento.Grupo.Local.Organizacao.Papel.Pessoa.Quantidade", 
						"Local": "Data.Documento.Local.Papel.Quantidade", 
						"Data": "Data.Documento.Local.Papel.Quantidade", 
						"Grupo": "Documento.Evento.Grupo.Papel.Pessoa.Quantidade", 
						"Pessoa": "Documento.Evento.Grupo.Papel.Pessoa.Quantidade"}

alter_entities_mapper_2 = {"Papel": "Documento.Evento.Grupo.Papel.Pessoa.Quantidade", 
							"Quantidade": "Documento.Evento.Grupo.Papel.Pessoa.Quantidade", 
							"Evento": "Documento.Evento.Grupo.Papel.Pessoa.Quantidade", 
							"Documento": "Data.Documento.Grupo.Organizacao.Papel.Quantidade", 
							"Organizacao": "Data.Documento.Grupo.Organizacao.Papel.Quantidade", 
							"Data": "Data.Documento.Grupo.Organizacao.Papel.Quantidade", 
							"Grupo": "Data.Documento.Grupo.Organizacao.Papel.Quantidade", 
							"Pessoa": "Documento.Evento.Grupo.Papel.Pessoa.Quantidade"}

alter_entities_mapper_3 = {"Papel": "Data.Documento.Local.Papel.Quantidade", 
							"Quantidade": "Data.Evento.Organizacao.Quantidade", 
							"Evento": "Data.Evento.Organizacao.Quantidade", 
							"Documento": "Data.Documento.Evento.Grupo.Local.Organizacao.Papel.Pessoa.Quantidade", 
							"Organizacao": "Data.Documento.Grupo.Organizacao.Papel.Quantidade", 
							"Local": "Data.Documento.Local.Papel.Quantidade", 
							"Data": "Data.Evento.Organizacao.Quantidade", 
							"Grupo": "Documento.Grupo.Pessoa", 
							"Pessoa": "Documento.Grupo.Pessoa"}


file = open("../entity_best_combinations", "r")
lines = file.readlines()
file.close()

for line in lines:
	tk = line.split("\t")
	entities_mapper[tk[0]] = tk[1].replace("\n", "")

for exp in range(len(experiments)):

	if exp == 0:
		resume_precision = [["\t", "cutoff_0", "cutoff_1", "cutoff_2", 
							"cutoff_3", "cutoff_4", "cutoff_5", "cutoff_6", 
							"cutoff_7", "cutoff_8", "cutoff_9"]]
		resume_recall 	= 	[["\t", "cutoff_0", "cutoff_1", "cutoff_2", 
							"cutoff_3", "cutoff_4", "cutoff_5", "cutoff_6", 
							"cutoff_7", "cutoff_8", "cutoff_9"]]
		resume_f1 	=		[["\t", "cutoff_0", "cutoff_1", "cutoff_2", 
							"cutoff_3", "cutoff_4", "cutoff_5", "cutoff_6", 
							"cutoff_7", "cutoff_8", "cutoff_9"]]

		for key in alter_entities_mapper:
			p = [key]
			r = [key]
			f1 = [key]
			
			for cutoff in range(0, 9, 1):	
				name_file = experiments[exp] + "result.NAIVEBAYES.Cutoff.{}.{}".format(cutoff, alter_entities_mapper[key])
				file = open(name_file, "r")
				text_lines = file.readlines()
				file.close()

				for tl in text_lines:
					if key in tl:
						tokens = re.split(r"\s+", tl)
						p.append(tokens[3].replace(";", "").replace("%", "").replace(",", "."))
						r.append(tokens[5].replace(";", "").replace("%", "").replace(",", "."))
						f1.append(tokens[7].replace(";", "").replace("%", "").replace(",", "."))
						break

			resume_precision.append(p)
			resume_recall.append(r)
			resume_f1.append(f1)

		write_resume(resume_files[exp], resume_precision, resume_recall, resume_f1)

	elif exp == 1:
		resume_precision = [["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]
		resume_recall 	= 	[["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]
		resume_f1 	=		[["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]

		for key in alter_entities_mapper_2:
			p = [key]
			r = [key]
			f1 = [key]
			
			for iteration in range(100, 300, 25):	
				name_file = experiments[exp] + "result.MAXENT.Iterations.{}.Cutoff.0.{}".format(iteration, alter_entities_mapper_2[key])
				file = open(name_file, "r")
				text_lines = file.readlines()
				file.close()

				for tl in text_lines:
					if key in tl:
						tokens = re.split(r"\s+", tl)
						p.append(tokens[3].replace(";", "").replace("%", "").replace(",", "."))
						r.append(tokens[5].replace(";", "").replace("%", "").replace(",", "."))
						f1.append(tokens[7].replace(";", "").replace("%", "").replace(",", "."))
						break

			resume_precision.append(p)
			resume_recall.append(r)
			resume_f1.append(f1)

		write_resume(resume_files[exp], resume_precision, resume_recall, resume_f1)

	elif exp == 2:
		
		resume_precision = [["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]
		resume_recall 	= 	[["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]
		resume_f1 	=		[["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]

		for key in alter_entities_mapper_3:
			p = [key]
			r = [key]
			f1 = [key]
			
			for iteration in range(100, 300, 25):	
				name_file = experiments[exp] + "result.PERCEPTRON.Iterations.{}.Cutoff.2.{}".format(iteration, alter_entities_mapper_3[key])
				file = open(name_file, "r")
				text_lines = file.readlines()
				file.close()

				for tl in text_lines:
					if key in tl:
						tokens = re.split(r"\s+", tl)
						p.append(tokens[3].replace(";", "").replace("%", "").replace(",", "."))
						r.append(tokens[5].replace(";", "").replace("%", "").replace(",", "."))
						f1.append(tokens[7].replace(";", "").replace("%", "").replace(",", "."))
						break

			resume_precision.append(p)
			resume_recall.append(r)
			resume_f1.append(f1)

		write_resume(resume_files[exp], resume_precision, resume_recall, resume_f1)

	elif exp == 3:

		resume_precision = [["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]
		resume_recall 	= 	[["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]
		resume_f1 	=		[["\t", "100", "125", "150", "175", 
							"200", "225", "250", "275"]]

		for key in alter_entities_mapper_3:
			p = [key]
			r = [key]
			f1 = [key]
			
			for iteration in range(100, 301, 100):	
				name_file = experiments[exp] + "result.MAXENT_QN.Iterations.{}.Cutoff.0.L1Cost.0.0.L2Cost.0.0.NumOfUpdates.1.MaxFctEval.20000.{}".format(iteration, alter_entities_mapper_3[key])
				file = open(name_file, "r")
				text_lines = file.readlines()
				file.close()

				for tl in text_lines:
					if key in tl:
						tokens = re.split(r"\s+", tl)
						p.append(tokens[3].replace(";", "").replace("%", "").replace(",", "."))
						r.append(tokens[5].replace(";", "").replace("%", "").replace(",", "."))
						f1.append(tokens[7].replace(";", "").replace("%", "").replace(",", "."))
						break

			resume_precision.append(p)
			resume_recall.append(r)
			resume_f1.append(f1)

		write_resume(resume_files[exp], resume_precision, resume_recall, resume_f1)

	elif exp == 4:
		command = "cd " + experiments[exp] + "; ../../consolidator.sh;"
		out = os.popen(command).read()

		lines = out.split("\n")

		file = open(resume_files[exp], "w")
		file.write("\tPrecision\tRecall\tF1\n")

		for line in lines[0:-1]:
			tokens = re.split(r"\s+", line.replace(";", "").replace("%", "").replace(",", "."))
			file.write(tokens[1] + "\t" + tokens[3] + "\t" + tokens[5] + "\t" + (tokens[7] + "\n").replace(".\n", "\n"))

		file.close()
		
		