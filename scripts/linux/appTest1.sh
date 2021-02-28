java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.MarketApplication JmenFish &
(sleep 5
java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.SellerApplication Aquaman &
java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication Bob &
java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication Patrick)
