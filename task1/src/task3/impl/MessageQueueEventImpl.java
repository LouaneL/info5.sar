package task3.impl;

import task1.impl.ChannelImpl;
import task3.givenCode.MessageQueueEvent;

public class MessageQueueEventImpl extends MessageQueueEvent{
	ChannelImpl channel;
	Listener listener;
	Boolean isClosed = false;
	MessageQueueEventImpl messageQueueConnexion;
	
	public MessageQueueEventImpl(ChannelImpl channel, MessageQueueEventImpl messageQueueConnexion) {
		this.channel = channel;
		this.messageQueueConnexion = messageQueueConnexion;
	}

	@Override
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public boolean send(Byte[] bytes) {
		Boolean response = send(bytes, 0, bytes.length);
		
		return response;
	}

	@Override
	public boolean send(Byte[] bytes, int offset, int length) {
		if (isClosed) {
			System.out.println("The MessageQueue is closed");
			return false;
		}
		
		channel.write(bytes, offset, length);
		
		Byte[] msgToSend = new Byte[length];
		System.arraycopy(bytes, offset, msgToSend, 0, length);
		
		listener.received(msgToSend);
		
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
