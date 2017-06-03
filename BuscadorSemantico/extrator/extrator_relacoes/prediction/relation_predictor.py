import csv
import numpy as np
from sklearn.naive_bayes import GaussianNB
from sklearn import svm

def read_csv(path):
	with open(path, 'r') as f:
		wines = list(csv.reader(f, delimiter=","))
	
	wines = np.array(wines[:], dtype=np.float)
	# wines[:, 3] /= np.amax(wines[:, 3])

	train, test = split(wines)

	data_train = train[:, : -1]
	relation_train = train[:, -1]

	data_test = test[:, : -1]
	relation_test = test[:, -1]

	return (data_train, relation_train, data_test, relation_test)

def split(data, size=.5):
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
	# print("Precisão: ", win/(win+errors))
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

	print("\n")
	print("Precision (Rel): ", (tp/(tp+tn)))
	print("Precision (NRel): ", (fn/(fn+fp)))
	print("Precision (Total): ", ((tp+fn)/(tp+tn+fn+fp)))
	print("-----------------------------------------------")
	print("Recall (Rel): ", (tp/(tp+fp)))
	print("Recall (NRel): ", (fn/(fn+tn)))
	print("Recall (Total): ", ((tp+fn)/(tp+fp+fn+tn)))
	print("-----------------------------------------------")
	print("F1 (Rel): ", (2*(tp/(tp+tn))*(tp/(tp+fp)))/((tp/(tp+tn))+(tp/(tp+fp))))
	print("F1 (NRel): ", (2*(fn/(fn+fp))*(fn/(fn+tn)))/((fn/(fn+fp))+(fn/(fn+tn))))
	print("F1 (Total): ", (2*((tp+fn)/(tp+tn+fn+fp))*((tp+fn)/(tp+fp+fn+tn)))/(((tp+fn)/(tp+tn+fn+fp))+((tp+fn)/(tp+fp+fn+tn))))

def main(model):
	if model == "bayes":
		print("Realizando predição utilizando o Naive Bayes")
		data_train, relation_train, data_test, relation_test = read_csv("./consolidade_tuples_final.csv")

		model = build_model(data_train, relation_train)
		predicted = model.predict(data_test)

		calc_errors(predicted, relation_test)
		confusion_matrix(predicted, relation_test)

	elif model == "svm":
		print("Realizando predição utilizando o SVM")
		data_train, relation_train, data_test, relation_test = read_csv("./consolidade_tuples_final.csv")

		# clf = svm.SVC(kernel='linear', C=C).fit(data_train[:-1], relation_train[:-1])
		# clf = svm.LinearSVC(C=C).fit(data_train[:-1], relation_train[:-1])
		clf = svm.SVC(gamma=0.001, C=10., class_weight={0:20, 1:10})
		clf.fit(data_train[:-1], relation_train[:-1])

		predicted = clf.predict(data_test[:-1])

		calc_errors(predicted, relation_test)
		confusion_matrix(predicted, relation_test)

	else:
		print("Os modelos disponíveis são: bayes e svm")
		exit(1)

if __name__ == '__main__':
	main("svm")
