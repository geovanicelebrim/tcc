import unicodedata

def getEntitiesAndRelations(fileName):
	file = open(fileName, "r", encoding="utf-8")
	lines = file.readlines()
	file.close()

	entities = dict()
	relations = dict()

	for line in lines:
		if "T" in line.split('\t')[0]:
			entities[line.split('\t')[0]] = line
		elif "R" in line.split('\t')[0]:
			relations[line.split('\t')[0]] = line.split('\t')[1]

	return (entities, relations)

def remove_accents(word):
	return "".join((c for c in unicodedata.normalize('NFD', word) if unicodedata.category(c) != 'Mn'))

def getSlice(fileName, begin=0, end=0):
	file = open(fileName, "r", encoding="utf-8")
	text = str(file.read())
	file.close()
	
	text = remove_accents(text[begin:end]).lower()
	
	return text

def build_dictionary(fileName):

	entities, relations = getEntitiesAndRelations(fileName)
	dictionary = dict()

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

def normalize(dictionary):
	max_freq = 0
	for ind in dictionary:
		if dictionary[ind] > max_freq:
			max_freq = dictionary[ind]

	for ind in dictionary:
		dictionary[ind] = dictionary[ind]/max_freq

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

	return normalize(final_dictionary)

def computer_pertinence(text, dictionary):
	pertinence = 1
	count = 0
	for token in text.split(' '):
		try:
			pertinence *= dictionary[token]
			pass
		except Exception:
			pertinence *= 0.2
		pass
		count += 1
	return (pertinence, count)
