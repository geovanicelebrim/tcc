import csv
import numpy as np
from sklearn.naive_bayes import GaussianNB
from sklearn import svm

def read_csv(path):
	with open(path, 'r') as f:
		wines = list(csv.reader(f, delimiter=","))
	
	wines = np.array(wines[:], dtype=np.float)

	train, test = split(wines)

	data_train = train[:, :4]
	relation_train = train[:, 4]

	data_test = test[:, :4]
	relation_test = test[:, 4]

	return (data_train, relation_train, data_test, relation_test)

def split(data, size=.6):
	np.random.shuffle(data)
	train = data[:int(len(data)*size)]
	test = data[len(train):]

	return (train, test)

def build_model(x, y):
	model = GaussianNB()
	model.fit(x, y)

	return model

def calc_errors(predicted, rigth):
	win = 0
	errors = 0
	for x in range(len(predicted)):
		if predicted[x] == rigth[x]:
			win += 1
		else:
			errors += 1
	print("Precisão: ", win/(win+errors))
	print("Classificados corretamente: ", win)
	print("Classificados erroneamente: ", errors)

def confusion_matrix(predicted, rigth):
	tp = 0
	tn = 0
	fp = 0
	fn = 0
	for x in range(len(predicted)):
		if predicted[x] == rigth[x] and rigth[x] == 0.0:
			fn += 1
		elif predicted[x] == 0.0 and rigth[x] == 1.0:
			fp += 1
		if predicted[x] == rigth[x] and rigth[x] == 1.0:
			tp += 1
		elif predicted[x] == 1.0 and rigth[x] == 0.0:
			tn += 1
	print("\n\tnegative\tpositive")
	print("false\t", fn, "\t\t", fp)
	print("true\t", tn, "\t\t", tp)

def main(model):
	if model == "bayes":
		print("Realizando predição utilizando o Naive Bayes")
		data_train, relation_train, data_test, relation_test = read_csv("./out.csv")

		model = build_model(data_train, relation_train)
		predicted = model.predict(data_test)

		calc_errors(predicted, relation_test)
		confusion_matrix(predicted, relation_test)

	elif model == "svm":
		print("Realizando predição utilizando o SVM")
		data_train, relation_train, data_test, relation_test = read_csv("./out.csv")

		clf = svm.SVC(gamma=0.001, C=100.)
		clf.fit(data_train[:-1], relation_train[:-1])

		predicted = clf.predict(data_test[:-1])

		calc_errors(predicted, relation_test)
		confusion_matrix(predicted, relation_test)

	else:
		print("Os modelos disponíveis são: bayes e svm")
		exit(1)

if __name__ == '__main__':
	main("bayes")
