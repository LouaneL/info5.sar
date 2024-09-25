package impl;

import java.util.HashMap;
import java.util.Map;

public class BrokerManager {
	static private BrokerManager instance;
	HashMap<String,BrokerImpl> brokers;
	
	private BrokerManager() {
		brokers = new HashMap<String,BrokerImpl>();
	}
	
	public static BrokerManager getInstance() {
		if (instance == null) {
			instance = new BrokerManager();
		}
		return instance;
	}
	
	public synchronized BrokerImpl getBroker(String name) {
		return brokers.get(name);
	}
	
	public synchronized void addBroker(BrokerImpl broker) {
		String name = broker.getName();
		if (getBroker(name) != null) {
			throw new IllegalStateException("Boker already exists in the BrokerManager");
		}
		brokers.put(name , broker);
	}
	
	public synchronized BrokerImpl removeBroker(String name) {
		return brokers.remove(name);
	}
}
