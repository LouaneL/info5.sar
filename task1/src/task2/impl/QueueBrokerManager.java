package task2.impl;

import java.util.HashMap;

public class QueueBrokerManager {
	static private QueueBrokerManager instance;
	HashMap<String, QueueBrokerImpl> queueBrokers;

	private QueueBrokerManager() {
		queueBrokers = new HashMap<String, QueueBrokerImpl>();
	}

	public static QueueBrokerManager getInstance() {
		if (instance == null) {
			instance = new QueueBrokerManager();
		}
		return instance;
	}

	public synchronized QueueBrokerImpl getBroker(String name) {
		return queueBrokers.get(name);
	}

	public synchronized void addBroker(QueueBrokerImpl queueBroker) {
		String name = queueBroker.getName();
		if (getBroker(name) != null) {
			throw new IllegalStateException("Boker already exists in the BrokerManager");
		}
		queueBrokers.put(name, queueBroker);
	}

	public synchronized QueueBrokerImpl removeBroker(String name) {
		return queueBrokers.remove(name);
	}
}
