package task2.impl;

import task1.impl.BrokerImpl;
import task1.impl.ChannelImpl;
import task2.givencode.QueueBroker;

public class QueueBrokerImpl extends QueueBroker{
	String name;
	BrokerImpl broker;
	QueueBrokerManager queueBrokerManager;
	
	public QueueBrokerImpl(String name, QueueBrokerManager queueBrokerManager, BrokerImpl broker){
		this.name = name;
		this.queueBrokerManager = queueBrokerManager;
		this.broker = broker;
		queueBrokerManager.addBroker(this);
	}
	
	public String getName() {
		return name;
	}
	
	public BrokerImpl getBroker() {
		return broker;
	}
	
	public synchronized MessageQueueImpl accept(int port) {
		ChannelImpl channel = broker.accept(port);
		return new MessageQueueImpl(channel);
	}
	
	public synchronized MessageQueueImpl connect(String name, int port) {
		QueueBrokerImpl queueBroker = queueBrokerManager.getBroker(name);
		if (queueBroker == null) {
			throw new IllegalStateException("QueueBroker: "+name+" doesn't exist");
		}
		
		ChannelImpl channel = broker.connect(queueBroker.getBroker().getName(), port);
		return new MessageQueueImpl(channel);
	}
	
}
