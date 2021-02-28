start java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.MarketApplication JmenFish
sleep 5
start java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.SellerApplication Aquaman
start java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication Bob
start java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication Patrick
