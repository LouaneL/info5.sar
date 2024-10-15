package task3.impl;

import task3.givenCode.Message;

public class SendEventTask extends EventTask{
	private MessageQueueEventImpl messageQueue;

	public SendEventTask(MessageQueueEventImpl messageQueue, Byte[] bytes, int offset, int length) {
		this.messageQueue = messageQueue;
		this.runnable = new Runnable() {
			@Override
			public void run() {		
				System.out.println("In SendEventTask");
				Byte[] lengthToSend = messageQueue.intToBytes(length);
				messageQueue.channel.write(lengthToSend, 0, lengthToSend.length);
				
				int cpt = offset;
				while(cpt < length) {
					cpt += messageQueue.channel.write(bytes, cpt, length-cpt);
				}
				
				Byte[] msgToSend = new Byte[length];
				System.arraycopy(bytes, offset, msgToSend, 0, length);
				
				messageQueue.listenerConnexion.received(msgToSend);
			}
		};
	}
}
