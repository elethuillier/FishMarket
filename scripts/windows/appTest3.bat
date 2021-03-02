# Start Market Application
start "1" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.MarketApplication MarketAgent
# sleep for 5 seconds
timeout /t 5
# Start Seller Application
start "2" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.SellerApplication SellerAgent
# Start Buyer Application 1er agent
start "3" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication BuyerAgent1
# Start Buyer Application 2nd agent
start "3" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication BuyerAgent2
# Start Buyer Application 3rd agent
start "3" java -Dfile.encoding=UTF-8 -classpath "../../out/production/FishMarket;../../lib/jade.jar" app.BuyerApplication BuyerAgent3
