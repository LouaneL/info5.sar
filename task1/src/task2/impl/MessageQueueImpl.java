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
		if (isClosed) {
			throw new IllegalStateException("MessageQueue is closed");
		}
		Byte[] lengthToSend = intToBytes(length);
		channel.write(lengthToSend, 0, 4);
		
		int cpt = offset;
		while (cpt < length) {
			cpt += channel.write(bytes, cpt, length-cpt);
		}
		
	}
	
	public synchronized Byte[] received() {
		if (isClosed) {
			throw new IllegalStateException("MessageQueue is closed");
		}
		
		Byte[] lengthToRead = new Byte[4];
		channel.read(lengthToRead, 0, 4);
		int length = bytesToInt(lengthToRead);
		
		Byte[] msg_received = new Byte[length];
		int cpt = 0;
		while (cpt < length) {
			cpt += channel.read(msg_received, cpt, length-cpt);
		}
		return msg_received;
	}
	
	public void close() {
		channel.disconnect();
		isClosed = true;
	}
	
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
