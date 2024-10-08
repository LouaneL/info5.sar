package task3.givenCode;

import task3.impl.MessageQueueEventImpl;

public abstract class QueueBrokerEvent {
	public QueueBrokerEvent() {}
	public QueueBrokerEvent(String name) {}
	
	public interface IAcceptListener {
		void accepted(MessageQueueEventImpl messageQueueEvent);
	}
	public abstract boolean bind(int port, IAcceptListener listener);
	public abstract boolean unbind(int port);
	
	public interface IConnectListener {
		void connected(MessageQueueEventImpl messageQueueEvent);
		void refused();
	}
	public abstract boolean connect(String name, int port, IConnectListener listener);
}
