#!/bin/sh

# initialise the search engine system
#
# Author:   Geovani Celebrim
# Version:  2016-12-19

#############################################
DIR=`dirname $0`
user=$USERNAME
path="/home/$user/WebServer"

if [ -d $path ]; then
	echo "The path: $path already exist!"
	echo "Please check it and try again."
	exit
fi
#############################################

#############################################
ann="$path/repository/ann"
data="$path/repository/data"
dictionary="$path/repository/dictionary"
meta="$path/repository/meta"

mkdir -p $ann
mkdir -p $data
mkdir -p $dictionary
mkdir -p $meta
#############################################

#############################################
cp "$DIR/stopWordsPt.txt" $dictionary
cp "$DIR/rest_query_template.py" $path
#############################################

#############################################
sudo apt-get install python-pip
sudo pip install --upgrade pip
sudo pip install setuptools
sudo pip install neo4jrestclient