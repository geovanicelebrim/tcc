# -*- coding: utf-8; -*-
import sys
from neo4j.v1 import GraphDatabase, basic_auth
driver = GraphDatabase.driver("bolt://ccufrrj.southcentralus.cloudapp.azure.com", auth=basic_auth("neo4j", "neo4j"))
# driver = GraphDatabase.driver("bolt://localhost", auth=basic_auth("neo4j", "neo4j"))

query = ['match (d:Documento) where d.nome = \"A-aventura-dos-pracinhas-brasileiros-na-Segunda-Guerra-Mundial.txt\" set d.autor = \"FREDERICO ROSAS\" set d.ano = 2014 set d.fonte = \"El País\"',
		 'match (d:Documento) where d.nome = \"De-improviso-na-maior-das-guerras-1-3.txt\" set d.autor = \"Diego Antonelli\" set d.ano = 2015 set d.fonte = \"Gazeta do Povo\"',
		 'match (d:Documento) where d.nome = \"A-cobra-realmente-fumou-2-3.txt\" set d.autor = \"Diego Antonelli\" set d.ano = 2015 set d.fonte = \"Gazeta do Povo\"',
		 'match (d:Documento) where d.nome = \"Adeus-Italia-adeus-pracinhas-3-3.txt\" set d.autor = \"Diego Antonelli\" set d.ano = 2015 set d.fonte = \"Gazeta do Povo\"']

session = driver.session()

for q in query:
	session.run(q)
	pass

session.close()