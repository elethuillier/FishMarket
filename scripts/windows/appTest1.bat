start "1" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.MarketApplication JmenFish
timeout /t 5
start "2" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.SellerApplication Aquaman
start "3" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication Bob
start "4" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication Patrick
