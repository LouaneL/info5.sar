package task3.givenCode;

public abstract class MessageQueueEvent {
	public interface Listener {
		void received(Byte[] msg);
		void closed();
	}
	public abstract void setListener(Listener l);
	
	public abstract boolean send(Byte[] bytes);
	public abstract boolean send(Byte[] bytes, int offset, int length);
	
	public abstract void close();
	public abstract boolean closed();
}
