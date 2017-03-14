#-*-coding:utf-8-*-
import os.path
import ast
import os
import threading
from limpador import clean

def exec(combinations, temp_name):

	for c in combinations:
		
		step = [item for item in entities if item not in c]

		os.system('cp -r ./tudo/* ./' + temp_name + '/')

		clean(step, temp_name)

		nome = "result"

		for n in c:
			nome += "." + str(n)

		os.system('./opennlp-1.7.2/bin/opennlp TokenNameFinderCrossValidator.brat -lang pt -annotationConfig ./' + temp_name + '/annotation.conf -bratDataDir ./' + temp_name + '/wikipedia/ -tokenizerModel ./opennlp-models/pt-token.bin -sentenceDetectorModel ./opennlp-models/pt-sent.bin -detailedF true ' + '> ./resultado/' + nome)
		os.system('tail -' + str(4 + len(c)) + ' ./resultado/' + nome + ' > ./resultado/' + nome + '.tmp')
		os.system('rm ./resultado/' + nome + ' && mv ./resultado/' + nome + '.tmp ./resultado/' + nome)

		os.system('rm -rf ./' + temp_name + '/*')

def init():
	os.system('mkdir temp1')
	os.system('mkdir temp2')
	os.system('mkdir temp3')
	os.system('mkdir temp4')
	os.system('sleep 1')

def end():
	os.system('rm -r ./temp1')
	os.system('rm -r ./temp2')
	os.system('rm -r ./temp3')
	os.system('rm -r ./temp4')



file = open("combinations", "r")

lines = file.readlines()
combinations = [[]]
del combinations[0]

for l in lines:
	combinations.append(ast.literal_eval(l))


entities = [	"Artefato", "Grupo", "Acontecimento", "Pesquisador", "Evento", "Papel", "Fonte", "Organizacao", "Local",
			"Documento", "Pessoa", "Data", "Quantidade"	]


# split = len(combinations)//4

# c1 = combinations[0:split]
# c2 = combinations[split:2*split]
# c3 = combinations[2*split:3*split]
# c4 = combinations[3*split:4*split]

c1 = combinations[0:3]
c2 = combinations[3:5]
c3 = combinations[5:8]
c4 = combinations[8:10]

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
