cd ../../
mkdir -p out/production/FishMarket
find -name "*.java" >sources.txt
javac -encoding UTF-8 -cp lib/jade.jar -d out/production/FishMarket @sources.txt
cp -r src/view out/production/FishMarket
