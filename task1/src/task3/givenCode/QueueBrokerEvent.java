package task3.givenCode;

import task3.impl.MessageQueueEventImpl;

public abstract class QueueBrokerEvent {
	public QueueBrokerEvent() {}
	public QueueBrokerEvent(String name) {}
	
	public interface AcceptListener {
		void accepted(MessageQueueEventImpl messageQueueEvent);
	}
	public abstract boolean bind(int port, AcceptListener listener);
	public abstract boolean unbind(int port);
	
	public interface ConnectListener {
		void connected(MessageQueueEventImpl messageQueueEvent);
		void refused();
	}
	public abstract boolean connect(String name, int port, ConnectListener listener);
}
