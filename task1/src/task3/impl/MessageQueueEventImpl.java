package task3.impl;

import java.net.BindException;
import java.util.LinkedList;

import task1.impl.ChannelImpl;
import task3.givenCode.Message;
import task3.givenCode.MessageQueueEvent;
import task3.givenCode.MessageQueueEvent.IListener;

public class MessageQueueEventImpl extends MessageQueueEvent{
	ChannelImpl channel;
	IListener listener;
	Boolean isClosed = false;
	LinkedList<Message> messagesQueue;
	
	public MessageQueueEventImpl(ChannelImpl channel) {
		this.channel = channel;
		messagesQueue = new LinkedList<Message>();
		sendThread.start();
		receiveThread.start();
	}
	
	private final Thread sendThread = new Thread(() -> {
		while (!isClosed) {
			if(!messagesQueue.isEmpty()) {
				Message msg = messagesQueue.removeLast();
				Byte[] bytes = msg.bytes;
				int offset = msg.offset;
				int length = msg.length;
				
				Byte[] lengthToSend = intToBytes(length);
				channel.write(lengthToSend, 0, lengthToSend.length);
				
				int cpt = offset;
				while(cpt < length) {
					cpt += channel.write(bytes, cpt, length-cpt);
				}
				
				EventTask event = new SendEventTask(this, msg);
				event.postTask();
			}
		}
	});
	
	private final Thread receiveThread = new Thread(() -> {
		while (!isClosed) {
			Byte[] lengthToRead = new Byte[4];
			channel.read(lengthToRead, 0, 4);
			int length = bytesToInt(lengthToRead);
			
			Byte[] msgReceived = new Byte[length];
			int cpt = 0;
			while(cpt < length) {
				cpt += channel.read(msgReceived, cpt, length-cpt);
			}
			
			EventTask event = new ReceiveEventTask(this, msgReceived);
			event.postTask();
		}
	});
	
	
	@Override
	public void setListener(IListener listener) {
		this.listener = listener;
	}

	public boolean send(Byte[] bytes) {		
		return send(bytes, 0, bytes.length);
	}

	@Override
	public boolean send(Byte[] bytes, int offset, int length) {
		if (isClosed) {
			System.out.println("The MessageQueue is closed");
			return false;
		}
		Message message = new Message(bytes, offset, length);
		messagesQueue.addFirst(message);
		
		return true;
	}

	@Override
	public void close() {
		sendThread.interrupt();
		receiveThread.interrupt();
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
