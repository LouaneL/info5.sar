package task3.impl;

import java.nio.channels.AcceptPendingException;
import java.util.HashMap;

import task1.impl.BrokerImpl;
import task1.impl.BrokerManager;
import task1.impl.ChannelImpl;
import task3.givenCode.QueueBrokerEvent;

public class QueueBrokerEventImpl extends QueueBrokerEvent {
	BrokerImpl broker;
	String name;
	QueueBrokerEventManager queueBrokerEventManager;
	ChannelImpl channelAccept;
	private HashMap<Integer, Thread> accepts;

	public QueueBrokerEventImpl(String name) {
		this.name = name;
	}

	public QueueBrokerEventImpl(String name, BrokerImpl broker, QueueBrokerEventManager queueBrokerEventManager) {
		this.name = name;
		this.broker = broker;
		this.queueBrokerEventManager = queueBrokerEventManager;
		queueBrokerEventManager.addBroker(this);
		accepts = new HashMap<Integer, Thread>();
	}

	@Override
	public boolean bind(int port, IAcceptListener listener) {
		if (getAccept(port) != null) {
			System.out.println("Bind on port " + port + " already exist");
			return false;
		}

		Thread acceptThread = new Thread() {
			@Override
			public void run() {
				channelAccept = broker.accept(port);
				MessageQueueEventImpl messageQueue = new MessageQueueEventImpl(channelAccept);
				AcceptEventTask event = new AcceptEventTask(listener, messageQueue);
				event.postTask();
			}
		};
		acceptThread.start();
		accepts.put(port, acceptThread);
		return true;
	}

	@Override
	public boolean unbind(int port) {
		if (getAccept(port) != null) {
			Thread acceptThread = accepts.remove(port);
			acceptThread.interrupt();
			return true;
		}
		return false;
	}

	@Override
	public boolean connect(String name, int port, IConnectListener listener) {
		Thread connectThread = new Thread() {
			@Override
			public void run() {
				try {
					QueueBrokerEventImpl queueBrokerAccept = queueBrokerEventManager.getBroker(name);
					BrokerImpl brokerAccept = queueBrokerAccept.getBroker();
					ChannelImpl channelConnect = broker.connect(brokerAccept.getName(), port);
					MessageQueueEventImpl messageQueue = new MessageQueueEventImpl(channelConnect);
					ConnectEventTask event = new ConnectEventTask(listener, messageQueue);
					event.postTask();
				} catch(Exception e) {
					System.err.println(e.getMessage());
					listener.refused();
				}
			}
		};
		
		connectThread.start();
		
		return true;
	}

	public String getName() {
		return name;
	}

	public BrokerImpl getBroker() {
		return broker;
	}

	public Thread getAccept(int port) {
		return accepts.get(port);
	}
}
