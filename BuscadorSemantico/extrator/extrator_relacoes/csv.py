import os
from dictionary import getEntitiesAndRelations, build_dictionary, consolidate_dictionary, getSlice, computer_pertinence

tuples = [[]]
consolidade_tuples = ["entity1,entity2,pertinence,dot,relation"]

def getSentences(fileName):
	file = open(fileName, "r", encoding="utf-8")
	sentences = file.readlines()
	file.close()

	return sentences

def getEntitiesOfSentencie(sentences, entities, indexOfSentencie=0):

	sentence_entities = {"":""}
	del sentence_entities[""]

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

	ent = []
	for e in entities_of_sentence:
		ent.append(e)

	# print(ent)
	for i in range(len(ent)):
		for j in range(i + 1,len(ent)):
			# print(ent[i], "\t", ent[j])

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

	dics = []
	for i in range(len(annFiles)):
		dics.append(build_dictionary(annFiles[i]))

	dictionary = consolidate_dictionary(dics)

	for i in range(1, len(tuples)):

		e1b = int(entities[tuples[i][1]].split('\t')[1].split(' ')[1])
		e1e = int(entities[tuples[i][1]].split('\t')[1].split(' ')[2])
		e2b = int(entities[tuples[i][2]].split('\t')[1].split(' ')[1])
		e2e = int(entities[tuples[i][2]].split('\t')[1].split(' ')[2])

		begin, end = e1e, e2b
		if e2b < e1e:
			begin, end = e2e, e1b

		text = getSlice(tuples[i][0], begin, end)
		pert = computer_pertinence(text, dictionary)
		
		tuples[i].append(pert)

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

	entities_type = {"":""}
	del entities_type[""]

	for i in range(1, len(tuples)):
		entities, relations = getEntitiesAndRelations(tuples[i][0].replace(".txt", ".ann"))

		e1_type = entities[tuples[i][1]].split('\t')[1].split(' ')[0]
		e2_type = entities[tuples[i][2]].split('\t')[1].split(' ')[0]

		try:
			entities_type[e1_type]
			pass
		except Exception:
			entities_type[e1_type] = len(entities_type) + 1
		pass

		try:
			entities_type[e2_type]
			pass
		except Exception:
			entities_type[e2_type] = len(entities_type) + 1
		pass

		r = 0
		if tuples[i][5]:
			r = 1
		# consolidade_tuples.append(str(entities_type[e1_type]) + "," + str(entities_type[e2_type]) + "," + "{:.20f}".format(tuples[i][3]) + "," + str(tuples[i][4]) + "," + str(r))
		consolidade_tuples.append(str(e1_type) + "," + str(e2_type) + "," + "{:.20f}".format(tuples[i][3]) + "," + str(tuples[i][4]) + "," + str(r))

	writeEntitiesType(entities_type, file_out)
	writeTuples(consolidade_tuples, file_out)

def writeEntitiesType(entities_type, file_out):
	file = open("entities_type_" + file_out + ".csv", "w")
	file.write("id,entity_type\n")
	for e in entities_type:
		file.write(str(entities_type[e]) + "," + e + "\n")
	file.close()

def writeTuples(consolidade_tuples, file_out):
	file = open("consolidade_tuples_" + file_out + ".csv", "w")
	for i in range(len(consolidade_tuples)):
		file.write(consolidade_tuples[i] + "\n")
	file.close()

if __name__ == '__main__':

	annFiles = ['./wikipedia/ditadura_no_brasil_1.ann']#, './wikipedia/ditadura_no_brasil_2.ann', './wikipedia/ditadura_no_brasil_3.ann', './wikipedia/ditadura_no_brasil_4.ann']
	txtFiles = ['./wikipedia/ditadura_no_brasil_1.txt']#, './wikipedia/ditadura_no_brasil_2.txt', './wikipedia/ditadura_no_brasil_3.txt', './wikipedia/ditadura_no_brasil_4.txt']

	for i in range(len(annFiles)):
		tuples.clear()
		ann = [annFiles[i]]
		txt = [txtFiles[i]]

		build_combinations(ann, txt)

		consolidate(str(i))