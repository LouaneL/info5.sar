package task2.givencode;

import task1.impl.ChannelImpl;

public abstract class MessageQueue {
	
	public MessageQueue() {}
	public MessageQueue(ChannelImpl channel) {}
	
	public abstract void send(Byte[] bytes, int offset, int length);
	
	public abstract Byte[] received();
	
	public abstract void close();
}
