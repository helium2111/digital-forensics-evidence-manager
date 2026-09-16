#!/bin/sh
mkdir -p out
javac -d out src/model/*.java src/service/*.java src/util/*.java src/Main.java || exit 1
java -cp out Main
