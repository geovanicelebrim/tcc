import os
import ast
import sys
from dictionary import getEntitiesAndRelations, build_dictionary, consolidate_dictionary, getSlice, computer_pertinence, computer_pertinence_bi_gram

tuples = list(list())
consolidade_tuples = list()
dictionary = dict()

def getSentences(fileName):
	file = open(fileName, "r", encoding="utf-8")
	sentences = file.readlines()
	file.close()

	return sentences

def getEntitiesOfSentencie(sentences, entities, indexOfSentencie=0):

	sentence_entities = dict()

	begin_limit = 0
	end_limit = 0
	for i in range(0, indexOfSentencie + 1):
		begin_limit = end_limit
		end_limit += len(sentences[i])

	for e in entities:
		begin = int(entities[e].split('\t')[1].split(' ')[1])
		end = int(entities[e].split('\t')[1].split(' ')[2])

		if begin >= begin_limit and end <= end_limit:
			sentence_entities[e] = entities[e]

	return sentence_entities

def generateCombinations(entities_of_sentence, txtFile):

	ent = list()
	for e in entities_of_sentence:
		ent.append(e)

	for i in range(len(ent)):
		for j in range(i + 1,len(ent)):
			ls = [txtFile, ent[i], ent[j]]
			tuples.append(ls)

	return tuples

def existRelation(e1, e2, relations):

	for rel in relations:
		
		arg1 = relations[rel].split(' ')[1].split(':')[1]
		arg2 = relations[rel].split(' ')[2].split(':')[1]

		if (e1 == arg1 and e2 == arg2) or (e1 == arg2 and e2 == arg1):
			return True

	return False

def addAnothersColumns(annFiles, entities, relations, read_dictionary = True):

	dics = list()
	for i in range(len(annFiles)):
		dics.append(build_dictionary(annFiles[i]))

	if read_dictionary:
		dictionary.update(load_dictionary())
	else:
		dictionary.update(consolidate_dictionary(dics))

	for i in range(len(tuples)):

		e1b = int(entities[tuples[i][1]].split('\t')[1].split(' ')[1])
		e1e = int(entities[tuples[i][1]].split('\t')[1].split(' ')[2])
		e2b = int(entities[tuples[i][2]].split('\t')[1].split(' ')[1])
		e2e = int(entities[tuples[i][2]].split('\t')[1].split(' ')[2])

		begin, end = e1e, e2b
		if e2b < e1e:
			begin, end = e2e, e1b

		text = getSlice(tuples[i][0], begin, end)
		pert, count = computer_pertinence(text, dictionary)
		
		tuples[i].append(pert)
		tuples[i].append(count)

		bi_gram_pert, bi_gram_count = computer_pertinence_bi_gram(text, dictionary)

		tuples[i].append(bi_gram_pert)
		tuples[i].append(bi_gram_count)

		if ('.' or '?' or '!') in text:
			tuples[i].append(1)
		else:
			tuples[i].append(0)

		rel = existRelation(tuples[i][1], tuples[i][2], relations)

		tuples[i].append(rel)


def build_combinations(annFiles, txtFiles):

	for i in range(len(annFiles)):
		
		entities, relations = getEntitiesAndRelations(annFiles[i])
		sentences = getSentences(txtFiles[i])

		for sent in range(len(sentences)):
			entities_of_sentence = getEntitiesOfSentencie(sentences, entities, sent)
			generateCombinations(entities_of_sentence, txtFiles[i])

	addAnothersColumns(annFiles, entities, relations)

def load_dictionary(path="dictionary.txt"):
	file = open(path, "r")
	content = file.readline()
	file.close()

	return ast.literal_eval(content)


def consolidate(write_dictionary = None):

	for i in range(len(tuples)):
		entities, relations = getEntitiesAndRelations(tuples[i][0].replace(".txt", ".ann"))

		id_1 = entities[tuples[i][1]].split('\t')[0].replace("T", "")
		id_2 = entities[tuples[i][2]].split('\t')[0].replace("T", "")
		e1_type = entities[tuples[i][1]].split('\t')[1].split(' ')[0]
		e2_type = entities[tuples[i][2]].split('\t')[1].split(' ')[0]

		r = 0
		if tuples[i][8]:
			r = 1
		consolidade_tuples.append((id_1, id_2, str(e1_type), str(e2_type), "{:.20f}".format(tuples[i][3]), 
									str(tuples[i][4]), str(tuples[i][5]), str(tuples[i][6]), str(tuples[i][7]), str(r)))
	if write_dictionary:
		file = open("dictionary.txt", "w")
		file.write(str(dictionary))
		file.close()

def writeTuples(consolidade_tuples, file_out="./prediction/consolidade_tuples_final.csv"):
	entities_type = dict()
	file = open(file_out, "w")
	for i in range(len(consolidade_tuples)):
		try:
			entities_type[consolidade_tuples[i][2]]
			pass
		except Exception:
			entities_type[consolidade_tuples[i][2]] = len(entities_type) + 1
		pass

		try:
			entities_type[consolidade_tuples[i][3]]
			pass
		except Exception:
			entities_type[consolidade_tuples[i][3]] = len(entities_type) + 1
		pass
		file.write(str(consolidade_tuples[i][0]) + "," + str(consolidade_tuples[i][1]) + "," + 
				str(entities_type[consolidade_tuples[i][2]]) + "," + str(entities_type[consolidade_tuples[i][3]]) + "," +
				str(consolidade_tuples[i][4]) + "," + str(consolidade_tuples[i][5]) + "," + 
				str(consolidade_tuples[i][6]) + "," + str(consolidade_tuples[i][7]) + "," + 
				str(consolidade_tuples[i][8]) + "," + str(consolidade_tuples[i][9]) + "\n")
	file.close()

if __name__ == '__main__':
	if len(sys.argv) < 4:
		print("ERRO: A entrada deve ser composta pelos parâmetros: ann_file, txt_file, out_file\n")
		print("\t ann_file: É o caminho do arquivo de saída do openNLP com as entidades anotadas.")
		print("\t txt_file: É o caminho do arquivo de texto que gerou a anotação do arquivo ann.")
		print("\t out_file: É o caminho do arquivo de saída no formato csv com as features extraídas.")
		print("\t\t   Este arquivo, juntamente com o arquivo ann deverão ser passados para o extrator de relações.\n")
		exit(1)


	build_combinations([sys.argv[1]], [sys.argv[2]])

	consolidate(write_dictionary=None)

	writeTuples(consolidade_tuples, sys.argv[3])