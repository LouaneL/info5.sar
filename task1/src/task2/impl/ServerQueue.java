package task2.impl;

import task1.impl.BrokerImpl;

public class ServerQueue implements Runnable{
	BrokerImpl broker;
	QueueBrokerImpl queueBroker;
	
	public ServerQueue(BrokerImpl broker) {
		this.broker = broker;
	}
	
	public ServerQueue(QueueBrokerImpl queueBroker) {
		this.queueBroker = queueBroker;
	}

	@Override
	public void run() {
				
	}
}
