import os
from dictionary import getEntitiesAndRelations, build_dictionary, consolidate_dictionary, getSlice, computer_pertinence, computer_pertinence_bi_gram

tuples = list(list())
consolidade_tuples = list()

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

def addAnothersColumns(annFiles, entities, relations):

	dics = list()
	for i in range(len(annFiles)):
		dics.append(build_dictionary(annFiles[i]))

	dictionary = consolidate_dictionary(dics)

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

def consolidate(file_out=""):

	for i in range(len(tuples)):
		entities, relations = getEntitiesAndRelations(tuples[i][0].replace(".txt", ".ann"))

		e1_type = entities[tuples[i][1]].split('\t')[1].split(' ')[0]
		e2_type = entities[tuples[i][2]].split('\t')[1].split(' ')[0]

		r = 0
		if tuples[i][8]:
			r = 1
		consolidade_tuples.append((str(e1_type), str(e2_type), "{:.20f}".format(tuples[i][3]), str(tuples[i][4]), str(tuples[i][5]), str(tuples[i][6]), str(tuples[i][7]), str(r)))

def writeTuples(consolidade_tuples, file_out="./prediction/consolidade_tuples_final.csv"):
	entities_type = dict()
	file = open(file_out, "w")
	for i in range(len(consolidade_tuples)):
		try:
			entities_type[consolidade_tuples[i][0]]
			pass
		except Exception:
			entities_type[consolidade_tuples[i][0]] = len(entities_type) + 1
		pass

		try:
			entities_type[consolidade_tuples[i][1]]
			pass
		except Exception:
			entities_type[consolidade_tuples[i][1]] = len(entities_type) + 1
		pass
		file.write(str(entities_type[consolidade_tuples[i][0]]) + "," + str(entities_type[consolidade_tuples[i][1]]) + "," +
				str(consolidade_tuples[i][2]) + "," + str(consolidade_tuples[i][3]) + "," + 
				str(consolidade_tuples[i][4]) + "," + str(consolidade_tuples[i][5]) + "," + 
				str(consolidade_tuples[i][6]) + "," + str(consolidade_tuples[i][7]) + "\n")
	file.close()

if __name__ == '__main__':

	annFiles = ['./wikipedia/ditadura_no_brasil_1.ann', './wikipedia/ditadura_no_brasil_2.ann', './wikipedia/ditadura_no_brasil_3.ann', './wikipedia/ditadura_no_brasil_4.ann']
	txtFiles = ['./wikipedia/ditadura_no_brasil_1.txt', './wikipedia/ditadura_no_brasil_2.txt', './wikipedia/ditadura_no_brasil_3.txt', './wikipedia/ditadura_no_brasil_4.txt']

	for i in range(len(annFiles)):
		tuples.clear()
		ann = [annFiles[i]]
		txt = [txtFiles[i]]

		build_combinations(ann, txt)

		consolidate(str(i))

	writeTuples(consolidade_tuples)