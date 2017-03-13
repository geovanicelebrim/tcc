#-*-coding:utf-8-*-
import os.path

paths = [#"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/data-pessoa-local/wikipedia/ditadura_no_brasil_1.ann",
# 		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/data-pessoa-local/wikipedia/A-aventura-dos-pracinhas-brasileiros-na-Segunda-Guerra-Mundial.ann",
# 		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/data-pessoa-local/wikipedia/A-cobra-realmente-fumou-2-3.ann",
# 		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/data-pessoa-local/wikipedia/Adeus-Italia-adeus-pracinhas-3-3.ann",
# 		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/data-pessoa-local/wikipedia/De-improviso-na-maior-das-guerras-1-3.ann",
		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/quantidade/wikipedia/ditadura_no_brasil_1.ann",
		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/quantidade/wikipedia/ditadura_no_brasil_2.ann",
		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/quantidade/wikipedia/ditadura_no_brasil_3.ann",
		"/home/geovani/git/tcc/BuscadorSemantico/extrator/data/data-brat-ditadura-separado/quantidade/wikipedia/ditadura_no_brasil_4.ann"
		]


tokens = [	"Artefato", "AutorReporter", "Duvida", "Grupo", "Acontecimento", "Pesquisador", "Evento", "Papel", "Fonte", "Organizacao", "Local",
			"Documento", "Pessoa", "Data"	]

for p in paths:
	print("Limpando arquivo " + p)
	file = open(p, "r")

	lines = file.readlines()
	list_final = []


	for l in lines:
		write_line = True
		for t in tokens:
			if t in l:
				write_line = None
				break

		if write_line:
			list_final.append(l)

	file.close()

	file = open(p, "w")

	for l in list_final:
		file.write(str(l))

	file.close()