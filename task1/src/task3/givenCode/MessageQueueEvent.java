package task3.givenCode;

public abstract class MessageQueueEvent {
	interface Listener {
		void received(byte[] msg);
		void closed();
	}
	abstract void setListener(Listener l);
	
	abstract boolean send(byte[] bytes);
	abstract boolean send(byte[] bytes, int offset, int length);
	
	abstract void close();
	abstract boolean closed();
}
