package task3.impl;

import task3.givenCode.Message;

public class ReceiveEventTask extends EventTask {
	private MessageQueueEventImpl messageQueue;

	public ReceiveEventTask(MessageQueueEventImpl messageQueue) {
		this.messageQueue = messageQueue;
		this.runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("In ReceiveEventTask");
				Byte[] lengthToRead = new Byte[4];
				messageQueue.channel.read(lengthToRead, 0, 4);
				int length = messageQueue.bytesToInt(lengthToRead);
				
				Byte[] msgReceived = new Byte[length];
				int cpt = 0;
				while(cpt < length) {
					cpt += messageQueue.channel.read(msgReceived, cpt, length-cpt);
				}
				
				Message msg = new Message(msgReceived, 0, length);
				
				messageQueue.listenerConnexion.sent(msg);
			}
		};
	}
}
