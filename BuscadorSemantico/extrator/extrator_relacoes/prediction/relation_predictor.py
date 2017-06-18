import csv
import sys
import numpy as np
from sklearn.naive_bayes import GaussianNB
from sklearn import svm
from sklearn.externals import joblib

def read_csv(path):
	with open(path, 'r') as f:
		wines = list(csv.reader(f, delimiter=","))
	
	wines = np.array(wines[:], dtype=np.float)
	# wines[:, 3] /= np.amax(wines[:, 3])

	wines = balanced_data(wines)

	train, test = split(wines)

	data_train = train[:, : -1]
	relation_train = train[:, -1]

	data_test = test[:, : -1]
	relation_test = test[:, -1]

	return (data_train, relation_train, data_test, relation_test)

def load_csv_for_predict(path, result=True):
	with open(path, 'r') as f:
		wines = list(csv.reader(f, delimiter=","))
	
	wines = np.array(wines[:], dtype=np.float)
	
	if result and len(wines[0]) > 9:
		return (wines[:, 0:9], wines[:, 9:])

	return wines[:, 0:9]

def write_prediction(path, data, predicted):
	file = open(path, "a")
	for l in range(len(predicted)):
		if predicted[l] == 1.0:
			file.write("R" + str(l) + "\tRelacao Arg1:T" + str(int(data[l,0])) + " Arg2:T" + str(int(data[l,1])) + "\n")
	file.close()

def balanced_data(data):
	size = 0
	for x in range(len(data)):
		if data[x, -1] == 1.0:
			size += 1
	
	new_data = list(list())
	l1 = 0
	i = 0
	c = 0
	while i < len(data):
		if data[i,-1] == 1.0:
			new_data.append(data[i])

		if data[i,-1] == 0.0 and l1 < size:
			new_data.append(data[i])
			l1 += 1
		i += 1

	return np.array(new_data[:])

def split(data, size=.3):
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

def save_model(clf, path="model.pkl"):
	joblib.dump(clf, path)

def load_model(path="model.plk"):
	return joblib.load(path) 

def main(build=None, model="svm", csv_file="./consolidade_tuples_final.csv", out_file="saida.ann"):
	if build:
		if model == "bayes":
			print("Realizando predição utilizando o Naive Bayes")
			data_train, relation_train, data_test, relation_test = read_csv(csv_file)

			model = build_model(data_train[:, 2:], relation_train)
			predicted = model.predict(data_test[:, 2:])

			save_model(model, "./models/bayes/bayes_model.plk")

			calc_errors(predicted, relation_test)
			confusion_matrix(predicted, relation_test)

		elif model == "svm":
			print("Realizando predição utilizando o SVM")
			data_train, relation_train, data_test, relation_test = read_csv(csv_file)

			# clf = svm.SVC(kernel='linear', C=C).fit(data_train[:-1], relation_train[:-1])
			# clf = svm.LinearSVC(C=C).fit(data_train[:-1], relation_train[:-1])
			clf = svm.SVC(gamma=0.001, C=10., class_weight={0:20, 1:10})

			clf.fit(data_train[:-1, 2:], relation_train[:-1])

			save_model(clf, "./models/svm/svm_model.plk")

			predicted = clf.predict(data_test[:-1, 2:])

			calc_errors(predicted, relation_test)
			confusion_matrix(predicted, relation_test)

		else:
			print("Os modelos disponíveis são: bayes e svm")
			exit(1)
	else:
		if model == "bayes":
			print("Realizando predição utilizando o Naive Bayes")
			# data, rel = load_csv_for_predict(csv_file, result=True)
			data = load_csv_for_predict(csv_file, result=None)

			model = load_model("./models/bayes/bayes_model.plk")
			predicted = model.predict(data[:, 2:])

			write_prediction(out_file, data, predicted)

			# calc_errors(predicted, rel)
			# confusion_matrix(predicted, rel)

		elif model == "svm":
			print("Realizando predição utilizando o SVM")
			# data, rel = load_csv_for_predict(csv_file, result=True)
			data = load_csv_for_predict(csv_file, result=None)

			clf = load_model("./models/svm/svm_model.plk")

			predicted = clf.predict(data[:-1, 2:])

			write_prediction(out_file, data, predicted)
			
			# calc_errors(predicted, rel)
			# confusion_matrix(predicted, rel)

		else:
			print("Os modelos disponíveis são: bayes e svm")
			exit(1)

if __name__ == '__main__':
	if len(sys.argv) < 4:
		print("ERRO: A entrada deve ser composta pelos parâmetros: model, csv_file, out_file\n")
		print("\t model:\t   Assume os valores 'bayes' e 'svm'")
		print("\t csv_file: É o caminho do arquivo de entrada para ser realizada a predição")
		print("\t out_file: É o caminho do arquivo de saída com as relações anotadas.")
		print("\t\t   Este arquivo deve ser o arquivo com as entidades anotadas de saida do openNLP.\n")
		exit(1)

	main(build=None, model=sys.argv[1], csv_file=sys.argv[2], out_file=sys.argv[3])
