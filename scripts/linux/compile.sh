cd ../../
mkdir -p out
javac -encoding UTF-8 -cp lib/jade.jar -d out @files.txt
mkdir -p out/production/FishMarket/view
cp src/view out/production/FishMarket/view