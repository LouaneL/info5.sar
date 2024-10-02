package task3.givenCode;

public abstract class QueueBrokerEvent {
	public QueueBrokerEvent(String name) {}
	
	interface AcceptListener {
		void accepted(MessageQueueEvent queue);
	}
	abstract boolean bind(int port, AcceptListener listener);
	abstract boolean unbind(int port);
	
	interface ConnectListener {
		void connected(MessageQueueEvent queue);
		void refused();
	}
	abstract boolean connect(String name, int port, ConnectListener listener);
}
