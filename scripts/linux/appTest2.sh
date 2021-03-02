# Start Market Application
java -Dfile.encoding=UTF-8 -classpath ../../out/production/FishMarket:../../lib/jade.jar app.MarketApplication MarketAgent &
# sleep for 5 seconds
sleep 5
# Start Seller Application
java -Dfile.encoding=UTF-8 -classpath ../../out/production/FishMarket:../../lib/jade.jar app.SellerApplication SellerAgent &
# Start Buyer Application 1er agent
java -Dfile.encoding=UTF-8 -classpath ../../out/production/FishMarket:../../lib/jade.jar app.BuyerApplication BuyerAgent1 &
# Start Buyer Application 2nd agent
java -Dfile.encoding=UTF-8 -classpath ../../out/production/FishMarket:../../lib/jade.jar app.BuyerApplication BuyerAgent2
