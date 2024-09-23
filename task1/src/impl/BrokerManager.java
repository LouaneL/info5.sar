package impl;

import java.util.Map;

public class BrokerManager {
	static Map<String,BrokerImpl> brokers;
	
	public BrokerImpl getBroker(String name) {
		return brokers.get(name);
	}
	
	public void addBroker(BrokerImpl broker) {
		brokers.put(broker.getName(), broker);
	}
	
	public BrokerImpl removeBroker(String name) {
		return brokers.remove(name);
	}
}
