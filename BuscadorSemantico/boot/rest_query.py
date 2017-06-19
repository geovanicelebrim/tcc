#!/usr/bin/env python
# -*- coding: utf-8 -*-

#https://neo4j-rest-client.readthedocs.io/en/latest/queries.html
import sys
from neo4jrestclient.client import GraphDatabase

user = sys.argv[1]
password = sys.argv[2]
query = ""

for i in range(3,len(sys.argv)):
	query += sys.argv[i] + " "

url = "http://localhost:7474/db/data/"

gdb = GraphDatabase(url, username=user, password=password)

results =  gdb.query(query, data_contents=True)

nodes = []
edges = []

idNodes = {"":""}
idEdges = {"":""}

for r in results.graph:
	for n in r["nodes"]:
		try:
			idNodes[n["id"]]
			pass
		except Exception, e:
			if n["labels"][0] != "Arquivo":
				node = "label: " + n["labels"][0] + ", id: " + n["id"] + ", trecho: " + n["properties"]["trecho"]
				nodes.append(node)
				idNodes[n["id"]] = n["id"]
			else:
				node = "label: " + n["labels"][0] + ", id: " + n["id"] + ", trecho: " + n["properties"]["nome"]
				nodes.append(node)
				idNodes[n["id"]] = n["id"]

			pass
		pass
	pass



for r in results.graph:
	for n in r["relationships"]:
		try:
			idEdges[n["id"]]
			pass
		except Exception, e:
			edge = "from: " + n["startNode"] + ", to: " + n["endNode"]
			edges.append(edge)
			idEdges[n["id"]] = n["id"]
			pass
		pass
	pass

saida = ""
for n in nodes:
	saida += "node, " + n + "\n"
	pass
 

for n in edges:
	saida += "edge, " + n + "\n"
	pass

print(saida.encode('utf-8'))