package task3.impl;

import task1.impl.BrokerImpl;
import task1.impl.BrokerManager;
import task1.impl.ChannelImpl;
import task3.givenCode.QueueBrokerEvent;

public class QueueBrokerEventImpl extends QueueBrokerEvent{
	BrokerImpl broker;
	String name;
	QueueBrokerEventManager queueBrokerEventManager;

	public QueueBrokerEventImpl(String name) {
		this.name = name;
	}
	
	public QueueBrokerEventImpl(String name, BrokerImpl broker, QueueBrokerEventManager queueBrokerEventManager) {
		this.name = name;
		this.broker = broker;
		this.queueBrokerEventManager = queueBrokerEventManager;
	}


	@Override
	public boolean bind(int port, AcceptListener listener) {
		ChannelImpl channel = broker.accept(port);
		if (broker.getRDV(port) != null) {
			MessageQueueEventImpl messageQueueEvent = new MessageQueueEventImpl(channel);
			listener.accepted(messageQueueEvent);
			return true;
		} else {
			
		}
		
		return false;
	}

	@Override
	public boolean unbind(int port) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean connect(String name, int port, ConnectListener listener) {
		ChannelImpl channel = broker.accept(port);
		QueueBrokerEventImpl acceptQueueBroker = queueBrokerEventManager.getBroker(name);
		BrokerImpl acceptBroker = acceptQueueBroker.getBroker();
		
		if (broker.getRDV(port) != null) {
			MessageQueueEventImpl messageQueueEvent = new MessageQueueEventImpl(channel);
			listener.connected(messageQueueEvent);
		}
		return false;
	}
	
	public String getName() {
		return name;
	}
	
	public BrokerImpl getBroker() {
		return broker;
	}
}
