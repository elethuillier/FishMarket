cd ../../
if not exist out (mkdir out\production\FishMarket)
javac -encoding UTF-8 -cp lib/jade.jar -d out\production\FishMarket @files.txt
xcopy /e /i /y src\view out\production\FishMarket\view
