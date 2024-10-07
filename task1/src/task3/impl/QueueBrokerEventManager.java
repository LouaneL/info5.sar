package task3.impl;

import java.util.HashMap;

import task1.impl.BrokerImpl;
import task1.impl.BrokerManager;

public class QueueBrokerEventManager {
	static private QueueBrokerEventManager instance;
	HashMap<String,QueueBrokerEventImpl> queueBrokers;
	
	private QueueBrokerEventManager() {
		queueBrokers = new HashMap<String,QueueBrokerEventImpl>();
	}
	
	public static QueueBrokerEventManager getInstance() {
		if (instance == null) {
			instance = new QueueBrokerEventManager();
		}
		return instance;
	}
	
	public synchronized QueueBrokerEventImpl getBroker(String name) {
		return queueBrokers.get(name);
	}
	
	public synchronized void addBroker(QueueBrokerEventImpl queueBroker) {
		String name = queueBroker.getName();
		if (getBroker(name) != null) {
			throw new IllegalStateException("Boker already exists in the BrokerManager");
		}
		queueBrokers.put(name , queueBroker);
	}
	
	public synchronized QueueBrokerEventImpl removeBroker(String name) {
		return queueBrokers.remove(name);
	}
}
