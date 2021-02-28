cd ../../
mkdir -p out/production/FishMarket
javac -encoding UTF-8 -cp lib/jade.jar -d out/production/FishMarket @files.txt
mkdir -p out/production/FishMarket/view
cp -r src/view out/production/FishMarket/view