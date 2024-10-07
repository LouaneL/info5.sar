package task3.impl;

import task1.impl.ChannelImpl;
import task3.givenCode.MessageQueueEvent;

public class MessageQueueEventImpl extends MessageQueueEvent{
	ChannelImpl channel;
	Listener listener;
	Boolean isClosed = false;
	
	public MessageQueueEventImpl(ChannelImpl channel) {
		this.channel = channel;
	}

	@Override
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public boolean send(Byte[] bytes) {
		Boolean response = send(bytes, 0, bytes.length);
		listener.received(bytes);
		
		return response;
	}

	@Override
	public boolean send(Byte[] bytes, int offset, int length) {
		int cpt = offset;
		while (cpt < length) {
			int bytesSend = channel.write(bytes, cpt, length-cpt);
			cpt += bytesSend;
		}
		return true;
	}

	@Override
	public void close() {
		channel.disconnect();
		isClosed = true;
	}

	@Override
	public boolean closed() {
		return isClosed;
	}

}
