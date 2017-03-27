import os

def getEntitiesAndRelations(fileName):
	file = open(fileName, "r", encoding="utf-8")
	lines = file.readlines()
	file.close()

	entities = {"":""}
	relations = {"":""}
	

	del entities[""]
	del relations[""]
	

	for line in lines:
		if "T" in line.split('\t')[0] :
			entities[line.split('\t')[0]] = line
		elif "R" in line.split('\t')[0] :
			relations[line.split('\t')[0]] = line.split('\t')[1]

	return (entities, relations)

def getSlice(fileName, begin=0, end=0):
	file = open(fileName, "r", encoding="utf-8")
	text = str(file.read())
	file.close()

	return text[begin:end]

def build_dictionary(fileName):

	entities, relations = getEntitiesAndRelations(fileName)
	dictionary = {"":""}
	del dictionary[""]

	for rel in relations:
		e1 = relations[rel].split(' ')[1].split(':')[1]
		e2 = relations[rel].split(' ')[2].split(':')[1]

		begin_e1 = int(entities[e1].split('\t')[1].split(' ')[1])
		end_e1 = int(entities[e1].split('\t')[1].split(' ')[2])
		begin_e2 = int(entities[e2].split('\t')[1].split(' ')[1])
		end_e2 = int(entities[e2].split('\t')[1].split(' ')[2])

		text = ""
		if end_e1 < begin_e2:
			text = getSlice(fileName.replace(".ann", ".txt"), end_e1, begin_e2)
		else:
			text = getSlice(fileName.replace(".ann", ".txt"), end_e2, begin_e1)

		for word in text.split(' '):
			try:
				dictionary[word] += 1
				pass
			except Exception:
				dictionary[word] = 1
				pass
			
			pass

	return dictionary

def consolidate_dictionary(list_disctionaries):

	final_dictionary = {"":""}
	del final_dictionary[""]
	
	for dic in list_disctionaries:
		for ind in dic:
			try:
				final_dictionary[ind] += dic[ind]
				pass
			except Exception:
				final_dictionary[ind] = dic[ind]
			pass
	return final_dictionary

def computer_pertinence(text, dictionary):
	pertinence = 0
	for token in text.split(' '):
		try:
			pertinence += dictionary[token]
			pass
		except Exception:
			pertinence = pertinence
		pass
	return pertinence

if __name__ == '__main__':

	d1 = build_dictionary('./wikipedia/ditadura_no_brasil_1.ann')
	d2 = build_dictionary('./wikipedia/ditadura_no_brasil_2.ann')
	d3 = build_dictionary('./wikipedia/ditadura_no_brasil_3.ann')
	d4 = build_dictionary('./wikipedia/ditadura_no_brasil_4.ann')

	dics = [d1, d2, d3, d4]

	print(len(d1) + len(d2) + len(d3) + len(d4))
	print(len(consolidate_dictionary(dics)))