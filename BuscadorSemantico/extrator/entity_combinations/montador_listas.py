#-*-coding:utf-8-*-
import os.path
import ast
import os
import threading
from limpador import clean
import numpy as np

def exec(combinations, temp_name):
	log_file = open(temp_name + ".log", 'w')
	count = 0
	size = len(combinations) * 3 * 2 * 1 * 3 * 3 * 2 * 1
	for c in combinations:
		for it in range(100, 301, 100):
			for param in range(0, 3, 2):
				for tr in range(1, 2):
					for l1 in np.arange(0.0, 0.51, 0.25):
						for l2 in np.arange(0.0, 0.51, 0.25):
							for upd in range(1, 16, 14):
								for mfe in range(20000, 20001, 10000):
									count += 1
									log_file.write(str(count/size) + "%  concluído...\n")
									log_file.flush()

									text = """# Algoritmo default
Algorithm=MAXENT_QN
Iterations="""
									text += str(it) + "\n"
									text += """Cutoff=""" + str(param) + "\n"
									text += """Threads=""" + str(tr) + "\n"
									text += """L1Cost=""" + str(l1) + "\n"
									text += """L2Cost=""" + str(l2) + "\n"
									text += """NumOfUpdates=""" + str(upd) + "\n"
									text += """MaxFctEval=""" + str(mfe)

									file = open("params.txt", 'w')
									file.write(text)
									file.close()
									
									step = [item for item in entities if item not in c]

									os.system('cp -r ./tudo/* ./' + temp_name + '/')

									clean(step, temp_name)

									nome = "result.MAXENT_QN.Iterations." + str(it) + ".Cutoff." + str(param) + ".L1Cost." + str(l1) + ".L2Cost." + str(l2) + ".NumOfUpdates." + str(upd) + ".MaxFctEval." + str(mfe)

									for n in c:
										nome += "." + str(n)

									os.system('./opennlp-1.7.2/bin/opennlp TokenNameFinderCrossValidator.brat -lang pt -annotationConfig ./' + temp_name + '/annotation.conf -bratDataDir ./' + temp_name + '/wikipedia/ -tokenizerModel ./opennlp-models/pt-token.bin -sentenceDetectorModel ./opennlp-models/pt-sent.bin -detailedF true -params params.txt' + '> ./resultado/' + nome)
									os.system('tail -' + str(4 + len(c)) + ' ./resultado/' + nome + ' > ./resultado/' + nome + '.tmp')
									os.system('rm ./resultado/' + nome + ' && mv ./resultado/' + nome + '.tmp ./resultado/' + nome)

									os.system('rm -rf ./' + temp_name + '/*')
	log_file.close()

def init():
	os.system('mkdir temp1')
	os.system('mkdir temp2')
	os.system('mkdir temp3')
	os.system('mkdir temp4')
	os.system('sleep 1')

def end():
	os.system('rm -rf ./temp1')
	os.system('rm -rf ./temp2')
	os.system('rm -rf ./temp3')
	os.system('rm -rf ./temp4')



file = open("best_combinations", "r")

lines = file.readlines()
combinations = [[]]
del combinations[0]

for l in lines:
	combinations.append(ast.literal_eval(l))


entities = [	"Artefato", "Grupo", "Acontecimento", "Pesquisador", "Evento", "Papel", "Fonte", "Organizacao", "Local",
			"Documento", "Pessoa", "Data", "Quantidade"	]


# split = len(combinations)//4

c1 = combinations[0:2]
c2 = combinations[2:4]
c3 = combinations[4:6]
c4 = combinations[6:8]


init()

t1 = threading.Thread(target=exec, args=(c1, "temp1"))
t2 = threading.Thread(target=exec, args=(c2, "temp2"))
t3 = threading.Thread(target=exec, args=(c3, "temp3"))
t4 = threading.Thread(target=exec, args=(c4, "temp4"))

t1.start()
t2.start()
t3.start()
t4.start()

t1.join()
t2.join()
t3.join()
t4.join()

end()
