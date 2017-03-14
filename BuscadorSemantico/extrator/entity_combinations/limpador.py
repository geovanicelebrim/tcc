#-*-coding:utf-8-*-
import os.path

def clean(tokens, temp_name='temp'):
	paths = [
			"./" + temp_name + "/wikipedia/ditadura_no_brasil_1.ann",
			"./" + temp_name + "/wikipedia/ditadura_no_brasil_2.ann",
			"./" + temp_name + "/wikipedia/ditadura_no_brasil_3.ann",
			"./" + temp_name + "/wikipedia/ditadura_no_brasil_4.ann"
		]

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
