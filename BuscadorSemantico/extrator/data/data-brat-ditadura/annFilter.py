import argparse
import os

parser = argparse.ArgumentParser(description='Ann Parser')

parser.add_argument('-e', '--entity', nargs='+', help='Entity name(s)', required=True)
parser.add_argument('-p', '--path', help='Path to ann file(s)', required=True)

args = vars(parser.parse_args())

targetEntitiesList = args['entity']

map(str.lower, targetEntitiesList)

annDirPath = args['path']

annFilePathList = list()

for fileName in os.listdir(annDirPath):
	if fileName.endswith('.ann'):
		annFilePathList.append(os.path.abspath(os.path.join(annDirPath, fileName)))

for annFilePath in annFilePathList:

	file = open(annFilePath, "r", encoding="utf-8")

	annFile = file.readlines()

	file.close()

	file = open(annFilePath, "w", encoding="utf-8")

	for line in annFile:
		entity = line.split('\t')[1].split(' ')[:1][0]
		if (entity.lower() in targetEntitiesList):
			file.write(line)

	file.close()
