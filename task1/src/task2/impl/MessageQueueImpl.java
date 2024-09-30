package task2.impl;

import task1.impl.ChannelImpl;
import task2.givencode.MessageQueue;

public class MessageQueueImpl extends MessageQueue{
	ChannelImpl channel;
	Boolean isClosed = false;
	
	public MessageQueueImpl(ChannelImpl channel) {
		this.channel = channel;
	}
	
	public synchronized void send(Byte[] bytes, int offset, int length) {
		int byteSend = 0;
		while (byteSend < length) {
			byteSend += channel.write(bytes, byteSend, length-byteSend);
		}
	}
	
	public Byte[] received() {
		Byte[] msg_received = null;
		channel.read(msg_received, 0, 0);
		return msg_received;
	}
	
	public void close() {
		channel.disconnect();
		isClosed = true;
	}
	
	public boolean closed() {
		return isClosed;
	}
}
