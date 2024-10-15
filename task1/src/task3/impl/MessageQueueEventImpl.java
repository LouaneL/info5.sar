package task3.impl;

import java.net.BindException;

import task1.impl.ChannelImpl;
import task3.givenCode.Message;
import task3.givenCode.MessageQueueEvent;
import task3.givenCode.MessageQueueEvent.IListener;

public class MessageQueueEventImpl extends MessageQueueEvent{
	ChannelImpl channel;
	IListener listener;
	IListener listenerConnexion;
	Boolean isClosed = false;
	
	public MessageQueueEventImpl(ChannelImpl channel) {
		this.channel = channel;
	}
	
	public void setMessageQueueConnexion(MessageQueueEventImpl messageQueueConnexion) {
		listenerConnexion = messageQueueConnexion.listener;
	}
	
	@Override
	public void setListener(IListener listener) {
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
		
		EventTask event = new SendEventTask(this, bytes, offset, length);
		event.postTask();
		
		return true;
	}
	
	public boolean receive() {
		EventTask event = new ReceiveEventTask(this);
		event.postTask();
		return true;
	}

	@Override
	public void close() {
		EventTask event = new CloseEventTask(this);
		event.postTask();
	}
		

	@Override
	public boolean closed() {
		return isClosed;
	}
	
	public ChannelImpl getChannel() {
		return channel;
	}

	public Byte[] intToBytes(int value) {
        return new Byte[] { (byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value };
    }

    public int bytesToInt(Byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }
}
