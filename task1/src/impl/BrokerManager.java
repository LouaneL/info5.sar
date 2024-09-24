package impl;

import java.util.HashMap;
import java.util.Map;

public class BrokerManager {
	static Map<String,BrokerImpl> brokers;
	
	public BrokerManager() {
		brokers = new HashMap<>();
	}
	
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
