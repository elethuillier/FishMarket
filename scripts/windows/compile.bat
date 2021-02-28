cd ../../
if not exist out (mkdir out)
javac -encoding UTF-8 -cp lib/jade.jar -d out @files.txt

