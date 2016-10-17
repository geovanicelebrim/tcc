# -*- coding: utf-8; -*-
import sys
from neo4j.v1 import GraphDatabase, basic_auth


arquivo = open('./CEDIM-II-GUERRA/A-aventura-dos-pracinhas-brasileiros-na-Segunda-Guerra-Mundial.ann', 'r')

# Entidades tera 5 atributos (ID, TipoEntidade, Trecho, Texto)
entidades = []
# Relacionamentos tera 4 atributos (ID, TipoRelacao, OrigemRelacao, DestinoRelacao)
relacionamentos = []
# Anotacoes tera 2 atributos (ID da relacao, Descricao da relacao)
anotacoes = []

query_entidades = {"":""}

query_relacoes = {"":""}

query_anotacoes = {"":""}

for linha in arquivo:
    valores = linha.replace("\n", "").split("\t")

    if "T" in valores[0]:
    	
    	atributos = []
    	
    	atributos.append(valores[0])
    	atributos.append(valores[1].split(" ")[0])
    	atributos.append(valores[1].split(" ")[1] + ", " + valores[1].split(" ")[2])
    	atributos.append(valores[2])

    	entidades.append(atributos)

    elif "R" in valores[0]:

    	atributos = []

    	atributos.append(valores[0])
    	atributos.append(valores[1].split(" ")[0])
    	atributos.append(valores[1].split(" ")[1].replace("Arg1:", ""))
    	atributos.append(valores[1].split(" ")[2].replace("Arg2:", ""))

    	relacionamentos.append(atributos)

    elif "#" in valores[0]:

    	atributos = []

    	atributos.append(valores[1].split(" ")[1])
    	atributos.append(valores[2])
    	anotacoes.append(atributos)

arquivo.close()

#Cria as queryes para criar as entidades
for x in entidades:

	try:
		query_entidades[x[3]]
		# Ação se a entidade já existe 
		novo_numeroCitacoes = int(query_entidades[x[3]].split("numeroCitacoes:")[1].split(",")[0])
		novo_numeroCitacoes += 1
		novo_id = query_entidades[x[3]].split("id:\"")[1].split("\",")[0] + "; " + x[0]
		nova_posicao = query_entidades[x[3]].split("posicao:\"")[1].split("\",")[0] + "; " + x[2]
		
		nova_query = "create(:" + x[1].replace("-", "") + "{id:\"" + novo_id + "\", posicao:\"" + nova_posicao + "\", trecho:\"" + x[3] + "\", numeroCitacoes:" + str(novo_numeroCitacoes) + ", numeroRelacoes:0})"

		query_entidades[x[3]] = nova_query
		
		pass
	except Exception, e:
		# Ação se a entidade ainda não existe 
		q = "create(:" + x[1].replace("-", "") + "{id:\"" + x[0] + "\", posicao:\"" + x[2] + "\", trecho:\"" + x[3] + "\", numeroCitacoes:1, numeroRelacoes:0})"

		query_entidades[x[3]] = q

		pass
	pass

#Cria as queryes para criar as relações
for x in relacionamentos:

	entidade1 = ""
	entidade2 = ""
	id_relacao = x[0]
	tipo_relacao = x[1]

	for e in entidades:
		if x[2] == e[0]:
			entidade1 = e[3]
			pass
		if x[3] == e[0]:
			entidade2 = e[3]
			pass
		pass

	q = "match (e1) where e1.trecho = \"" + entidade1 + "\" match (e2) where e2.trecho = \"" + entidade2 + "\" create (e1)-[:" + x[1] + "{id:\"" + id_relacao + "\", nota:\"\"}]->(e2)"

	try:
		query_relacoes[q]
		pass
	except Exception, e:
		query_relacoes[q] = q
		pass
	
	pass

#Cria as queryes para as anotações das relações
for x in anotacoes:

	q = "match (a)-[r]-(b) where r.id = \"" + x[0] + "\" set r.nota = \"" + x[1] + "\""
	
	query_anotacoes[q] = q

	pass


# Persiste no banco
driver = GraphDatabase.driver("bolt://localhost", auth=basic_auth("neo4j", "cedim"))


numeroQuery = len(query_entidades) - 1
print("Numero de query de entidades: " + str(numeroQuery))
cont = 0
for x in query_entidades:
	sys.stdout.write("\r%i de %i" % (cont, numeroQuery))
	sys.stdout.flush()

	session = driver.session()
	query = query_entidades[x]
	
	if query != "":
		resultado = session.run(query)
		cont += 1
		pass

	session.close()
	pass

print("\nNumero de query executadas: " + str(cont))


numeroQuery = len(query_relacoes) - 1
print("Numero de query de relações: " + str(numeroQuery))
cont = 0
for x in query_relacoes:
	sys.stdout.write("\r%i de %i" % (cont, numeroQuery))
	sys.stdout.flush()

	session = driver.session()
	query = query_relacoes[x]

	if query != "":
		resultado = session.run(query)
		cont += 1
		pass
	
	session.close()
	pass

print("\nNumero de query executadas: " + str(cont))



numeroQuery = len(query_anotacoes) - 1
print("Numero de query de anotações: " + str(numeroQuery))
cont = 0
for x in query_anotacoes:
	sys.stdout.write("\r%i de %i" % (cont, numeroQuery))
	sys.stdout.flush()

	session = driver.session()
	query = query_anotacoes[x]

	if query != "":
		resultado = session.run(query)
		cont += 1
		pass
	
	session.close()
	pass

print("\nNumero de query executadas: " + str(cont))