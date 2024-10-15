package task3.impl;

import java.util.HashMap;

import task1.impl.BrokerImpl;
import task1.impl.BrokerManager;
import task1.impl.ChannelImpl;
import task3.givenCode.QueueBrokerEvent;

public class QueueBrokerEventImpl extends QueueBrokerEvent{
	BrokerImpl broker;
	String name;
	QueueBrokerEventManager queueBrokerEventManager;
	ChannelImpl channelaccept;
	private HashMap<Integer, IAcceptListener> accepts;
	

	public QueueBrokerEventImpl(String name) {
		this.name = name;
	}
	
	public QueueBrokerEventImpl(String name, BrokerImpl broker, QueueBrokerEventManager queueBrokerEventManager) {
		this.name = name;
		this.broker = broker;
		this.queueBrokerEventManager = queueBrokerEventManager;
		accepts = new HashMap<Integer, IAcceptListener>();
	}

	@Override
	public boolean bind(int port, IAcceptListener listener) {
		channelaccept = broker.accept(port);
		if (getAccept(port) == null) {
			accepts.put(port, listener);
			return true;
		}
		return false;
	}

	@Override
	public boolean unbind(int port) {
		if (getAccept(port) != null) {
			accepts.remove(port);
			return true;
		}
		return false;
	}

	@Override
	public boolean connect(String name, int port, IConnectListener listener) {
		ChannelImpl channel = broker.connect(name, port);
		QueueBrokerEventImpl acceptQueueBroker = queueBrokerEventManager.getBroker(name);
		IAcceptListener acceptListener = acceptQueueBroker.getAccept(port);
		
		if (acceptListener != null) {
			MessageQueueEventImpl messageQueueEventAccept = new MessageQueueEventImpl(channelaccept);
			MessageQueueEventImpl messageQueueEventConnect = new MessageQueueEventImpl(channel);
			
			messageQueueEventAccept.setMessageQueueConnexion(messageQueueEventConnect);
			messageQueueEventConnect.setMessageQueueConnexion(messageQueueEventAccept);
			
			listener.connected(messageQueueEventConnect);
			acceptListener.accepted(messageQueueEventAccept);
			return true;
		} else {
			listener.refused();
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public BrokerImpl getBroker() {
		return broker;
	}
	
	public IAcceptListener getAccept(int port) {
		return accepts.get(port);
	}
}
