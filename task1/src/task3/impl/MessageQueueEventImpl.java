package task3.impl;

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
		
		Byte[] lengthToSend = intToBytes(length);
		channel.write(lengthToSend, 0, lengthToSend.length);
		
		int cpt = offset;
		while(cpt < length) {
			cpt += channel.write(bytes, cpt, length-cpt);
		}
		
		Byte[] msgToSend = new Byte[length];
		System.arraycopy(bytes, offset, msgToSend, 0, length);
		
		listenerConnexion.received(msgToSend);
		
		return true;
	}
	
	public boolean receive() {
		Byte[] lengthToRead = new Byte[4];
		channel.read(lengthToRead, 0, 4);
		int length = bytesToInt(lengthToRead);
		
		Byte[] msgReceived = new Byte[length];
		int cpt = 0;
		while(cpt < length) {
			cpt += channel.read(msgReceived, cpt, length-cpt);
		}
		
		Message msg = new Message(msgReceived, 0, length);
		
		listenerConnexion.sent(msg);
		return true;
	}

	@Override
	public void close() {
		channel.disconnect();
		isClosed = true;
		listenerConnexion.closed();
	}

	@Override
	public boolean closed() {
		return isClosed;
	}

	private Byte[] intToBytes(int value) {
        return new Byte[] { (byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value };
    }

    private int bytesToInt(Byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }
}
