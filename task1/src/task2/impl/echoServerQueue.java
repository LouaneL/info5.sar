package task2.impl;

import task1.impl.BrokerImpl;
import task1.impl.BrokerManager;
import task1.impl.Client;
import task1.impl.Server;
import task1.impl.TaskImpl;

public class echoServerQueue {
	
	public static void main(String[] args) throws InterruptedException {
		BrokerManager brokerManager = BrokerManager.getInstance();
		QueueBrokerManager queueBrokerManager = QueueBrokerManager.getInstance();
		
		BrokerImpl clientBroker = new BrokerImpl("clientBroker", brokerManager);
		BrokerImpl serverBroker = new BrokerImpl("serverBroker", brokerManager);
		
		QueueBrokerImpl clientQueueBroker = new QueueBrokerImpl("clientQueueBroker", queueBrokerManager, clientBroker);
		QueueBrokerImpl serverQueueBroker = new QueueBrokerImpl("serverQueueBroker", queueBrokerManager, serverBroker);
		
		ClientQueue clientRunnable = new ClientQueue(clientQueueBroker);
		ServerQueue serverRunnable = new ServerQueue(serverQueueBroker);
		
		TaskQueueImpl server = new TaskQueueImpl(serverQueueBroker, serverRunnable);
		TaskQueueImpl client = new TaskQueueImpl(clientQueueBroker, clientRunnable);
		
		client.join();
		server.join();
	}
}
