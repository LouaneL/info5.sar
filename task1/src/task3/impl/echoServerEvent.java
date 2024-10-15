package task3.impl;

import java.security.MessageDigest;

import task1.impl.BrokerImpl;
import task1.impl.BrokerManager;
import task3.givenCode.Message;
import task3.givenCode.MessageQueueEvent.IListener;
import task3.givenCode.PumpEvent;
import task3.givenCode.QueueBrokerEvent.IAcceptListener;
import task3.givenCode.QueueBrokerEvent.IConnectListener;
import task3.impl.echoServerEvent.AcceptListener;

public class echoServerEvent {

	public class AcceptListener implements IAcceptListener {
		@Override
		public void accepted(MessageQueueEventImpl messageQueueEvent) {
			ServerListener listener = new ServerListener(messageQueueEvent);
			messageQueueEvent.setListener(listener);
			System.out.println("Connexion accepted");
		}
	}

	public class ConnectListener implements IConnectListener {
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

	public class ServerEvent implements Runnable {
		QueueBrokerEventImpl queueBroker;

		public ServerEvent(QueueBrokerEventImpl queueBroker) {
			this.queueBroker = queueBroker;
		}

		@Override
		public void run() {
			AcceptListener listener = new AcceptListener();
			boolean res = queueBroker.bind(8080, listener);
			if (!res) {
				System.out.println("Server failed to bind");
				return;
			}
		}
	}

	public class ClientEvent implements Runnable {
		QueueBrokerEventImpl queueBroker;

		public ClientEvent(QueueBrokerEventImpl queueBroker) {
			this.queueBroker = queueBroker;
		}

		@Override
		public void run() {
			ConnectListener listener = new ConnectListener();
			boolean res = queueBroker.connect("serverQueueBroker", 8080, listener);
			if (!res) {
				System.out.println("Client failed to connect");
				return;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {

		Runnable clientRunnable = new Runnable() {
			public void run() {
				BrokerManager brokerManager = BrokerManager.getInstance();
				QueueBrokerEventManager queueBrokerManager = QueueBrokerEventManager.getInstance();

				BrokerImpl clientBroker = new BrokerImpl("clientBroker", brokerManager);

				QueueBrokerEventImpl clientQueueBroker = new QueueBrokerEventImpl("clientQueueBroker", clientBroker, queueBrokerManager);
			}
		};

		Runnable serverRunnable = new Runnable() {
			public void run() {
				BrokerManager brokerManager = BrokerManager.getInstance();
				QueueBrokerEventManager queueBrokerManager = QueueBrokerEventManager.getInstance();
				
				BrokerImpl serverBroker = new BrokerImpl("serverBroker", brokerManager);
				
				QueueBrokerEventImpl serverQueueBroker = new QueueBrokerEventImpl("serverQueueBroker", serverBroker, queueBrokerManager);
			}
		};
		
		System.out.println("Runnables created");

		new EventTask().post(serverRunnable);
		new EventTask().post(clientRunnable);
		
		System.out.println("Runnables posted");
		
		PumpEventImpl.getInstance().start();
	}
}
