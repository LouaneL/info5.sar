package task3.impl;

import java.security.MessageDigest;

import task1.impl.BrokerImpl;
import task1.impl.BrokerManager;
import task3.givenCode.Message;
import task3.givenCode.MessageQueueEvent.IListener;
import task3.givenCode.QueueBrokerEvent.IAcceptListener;
import task3.givenCode.QueueBrokerEvent.IConnectListener;

public class echoServerEvent {

	public static void main(String[] args) throws InterruptedException {

		Runnable clientRunnable = new Runnable() {
			public void run() {
				BrokerManager brokerManager = BrokerManager.getInstance();
				QueueBrokerEventManager queueBrokerManager = QueueBrokerEventManager.getInstance();

				BrokerImpl clientBroker = new BrokerImpl("clientBroker", brokerManager);

				QueueBrokerEventImpl clientQueueBroker = new QueueBrokerEventImpl("clientQueueBroker", clientBroker,
						queueBrokerManager);

				ConnectListener listener = new ConnectListener();
				boolean res = clientQueueBroker.connect("serverBroker", 80, listener);
				if (!res) {
					System.out.println("Client failed to connect");
					return;
				}
			}
		};

		Runnable serverRunnable = new Runnable() {
			public void run() {
				BrokerManager brokerManager = BrokerManager.getInstance();
				QueueBrokerEventManager queueBrokerManager = QueueBrokerEventManager.getInstance();

				BrokerImpl serverBroker = new BrokerImpl("serverBroker", brokerManager);

				QueueBrokerEventImpl serverQueueBroker = new QueueBrokerEventImpl("serverBroker", serverBroker,
						queueBrokerManager);
				
				AcceptListener listener = new AcceptListener();
				boolean res = serverQueueBroker.bind(80, listener);
				if (!res) {
					System.out.println("Server failed to bind");
					return;
				}
			}
		};

		System.out.println("Runnables created");

		new EventTask().post(serverRunnable);
		
		try {
            Thread.sleep(1000); // Wait for the server to starts
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		new EventTask().post(clientRunnable);

		System.out.println("Runnables posted");

		PumpEventImpl.getInstance().start();
	}

}

class AcceptListener implements IAcceptListener {
	@Override
	public void accepted(MessageQueueEventImpl messageQueueEvent) {
		ServerListener listener = new ServerListener(messageQueueEvent);
		messageQueueEvent.setListener(listener);
		System.out.println("Connexion accepted");
	}
}

class ConnectListener implements IConnectListener {
	@Override
	public void connected(MessageQueueEventImpl messageQueueEvent) {
		ClientListener listener = new ClientListener(messageQueueEvent);
		messageQueueEvent.setListener(listener);

		System.out.println("Connexion connected");

		Byte[] msgDebut = new Byte[10];
		for (int i = 0; i < 10; i++) {
			msgDebut[i] = (byte) i;
		}
		messageQueueEvent.send(msgDebut);
	}

	@Override
	public void refused() {
		System.out.println("Connexion refused");
	}
}

class ServerListener implements IListener {
	private MessageQueueEventImpl messageQueue;

	public ServerListener(MessageQueueEventImpl messageQueue) {
		this.messageQueue = messageQueue;
	}

	@Override
	public void received(Byte[] msg) {
		messageQueue.send(msg);
	}

	@Override
	public void sent(Message msg) {
		System.out.println("Client sent message");
	}

	@Override
	public void closed() {
		System.out.println("Connexion closed");
	}
}

class ClientListener implements IListener {
	private MessageQueueEventImpl messageQueue;

	public ClientListener(MessageQueueEventImpl messageQueue) {
		this.messageQueue = messageQueue;
	}

	@Override
	public void received(Byte[] msg) {
		int i = 0;
		while (i < 10 && ((byte) i) == msg[i]) {
			i++;
		}

		if (i == 10) {
			System.out.println("TEST PASSED");
		} else {
			System.out.println("TEST FAILED");
		}

		messageQueue.close();
	}

	@Override
	public void sent(Message msg) {
		System.out.println("Server sent message");
	}

	@Override
	public void closed() {
		System.out.println("Connexion closed");
	}
}

class ServerEvent implements Runnable {

	public ServerEvent() {
	}

	@Override
	public void run() {
		BrokerManager brokerManager = BrokerManager.getInstance();
		QueueBrokerEventManager queueBrokerManager = QueueBrokerEventManager.getInstance();

		BrokerImpl serverBroker = new BrokerImpl("serverBroker", brokerManager);

		QueueBrokerEventImpl serverQueueBroker = new QueueBrokerEventImpl("serverQueueBroker", serverBroker,
				queueBrokerManager);

		AcceptListener listener = new AcceptListener();
		boolean res = serverQueueBroker.bind(8080, listener);
		if (!res) {
			System.out.println("Server failed to bind");
			return;
		}
	}
}

class ClientEvent implements Runnable {

	public ClientEvent() {
	}

	@Override
	public void run() {

		BrokerManager brokerManager = BrokerManager.getInstance();
		QueueBrokerEventManager queueBrokerManager = QueueBrokerEventManager.getInstance();

		BrokerImpl clientBroker = new BrokerImpl("clientBroker", brokerManager);

		QueueBrokerEventImpl clientQueueBroker = new QueueBrokerEventImpl("clientQueueBroker", clientBroker,
				queueBrokerManager);
		ConnectListener listener = new ConnectListener();
		boolean res = clientQueueBroker.connect("serverQueueBroker", 8080, listener);
		if (!res) {
			System.out.println("Client failed to connect");
			return;
		}
	}
}
