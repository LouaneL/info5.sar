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
	}
	
	public String getName() {
		return name;
	}
	
	public MessageQueueImpl accept(int port) {
		ChannelImpl channel = broker.accept(port);
		return new MessageQueueImpl(channel);
	}
	
	public MessageQueueImpl connect(String name, int port) {
		ChannelImpl channel = broker.connect(name, port);
		return new MessageQueueImpl(channel);
	}
	
}
