import argparse
import subprocess
import sys
import os
import re

regex1 = re.compile(r'\s\n\n')
re.purge()
regex2 = re.compile(r'[^>]+> ([^<]+).*')
re.purge()
regex3 = re.compile(r'\s\s')
re.purge()

def processOriginalText(fileName):

	file = open(fileName, "r", encoding="utf-8")
	originalText = file.read()
	file.close()

	originalText = regex1.sub(r'\n\n', originalText)

	output = open(fileName, "w", encoding="utf-8")
	output.write(originalText)
	output.close()


def parseToAnn(nerText, fileName):

	nerText = regex1.sub(r'\n\n', nerText)

	annotationsText = re.findall( '<START[^>]*>[^<]*<END>*', nerText)
	pureText = re.split(r'<START[^>]*>[^<]*<END>', nerText)

	count = 0
	size = len(annotationsText)
	begin = 0
	end = 1

	output = open(fileName.replace(".txt", ".ann"), "w", encoding="utf-8")

	for text in pureText:
		if count < size:
			entityName = annotationsText[count].replace(r'<START:', r'').split(r'>')[0]
			textSlice = regex2.sub(r'\1', annotationsText[count])
			textSlice = regex3.sub(r' ', textSlice)

			begin = end + len(text) - 1
			end = begin + len(textSlice)

			output.write("%s\t%s %d %d\t%s\n" % ("T" + str(count + 1), entityName, begin, end - 1, textSlice))

			count += 1
			
	output.close()

def ner(fileName):

	nerCommandLine = opennlpExecutablePath + ' TokenNameFinder '
	nerCommandLine += modelPath + ' < '
	nerCommandLine += fileName

	nerProcessOutput = subprocess.getoutput(nerCommandLine)

	nerProcessOutput = '\n'.join(nerProcessOutput.split('\n')[1:-4])

	#nerFileName = fileName.replace(".txt", "_ner.txt")

	#nerOutput = open(nerFileName, "w", encoding="utf-8")
	#nerOutput.write(nerProcessOutput)
	#nerOutput.close()

	parseToAnn(nerProcessOutput, fileName)

parser = argparse.ArgumentParser(description='Named Entity Extractor')

parser.add_argument('-nlp', '--opennlp', help='path to the opennlp lib', required=True)
parser.add_argument('-m', '--model', help='path to the tokenNameFinder model', required=True)
parser.add_argument('-f', '--file', help='path to a single file', required=False)
parser.add_argument('-d', '--dir', help='path to a dir', required=False)

args = vars(parser.parse_args())

opennlpExecutablePath = args['opennlp'].strip()

if not os.path.isfile(opennlpExecutablePath):
	print('error: opennlp lib is not a file!')
	print(parser.print_usage())
	sys.exit(0)
else:
	opennlpExecutablePath = os.path.abspath(opennlpExecutablePath)

modelPath = args['model'].strip()

if not os.path.isfile(modelPath):
	print('error: model is not a file!')
	print(parser.print_usage())
	sys.exit(0)
else:
	modelPath = os.path.abspath(modelPath)

filePath = args['file']
dirPath = args['dir']
files = list()

if dirPath is not None and os.path.isdir(dirPath):

	for fileName in os.listdir(dirPath):
		if fileName.endswith('.txt'):
			files.append(os.path.abspath(os.path.join(dirPath, fileName)))

	if len(files) == 0:
		print('error: there are no text files in the given dir!')
		print(parser.print_usage())
		sys.exit(0)
	else:
		for fileName in files:
			processOriginalText(fileName)
			ner(fileName)

elif filePath is not None and os.path.isfile(filePath) and filePath.endswith('.txt'):
	filePath = os.path.abspath(filePath)
	processOriginalText(filePath)
	ner(filePath)

else:
	print('error: either dir/file param not given or the text file provided in the -f/--file param is not valid!')
	print(parser.print_usage())
	sys.exit(0)
