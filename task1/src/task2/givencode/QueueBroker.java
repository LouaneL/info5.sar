package task2.givencode;

import task1.impl.BrokerImpl;
import task2.impl.MessageQueueImpl;

public abstract class QueueBroker {
	
	public QueueBroker(BrokerImpl broker) {}
	public QueueBroker() {}
	
	public abstract String getName();
	
	public abstract MessageQueueImpl accept(int port);
	
	public abstract MessageQueueImpl connect(String name, int port);
	
}
