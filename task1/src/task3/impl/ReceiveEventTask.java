package task3.impl;

import task3.givenCode.Message;

public class ReceiveEventTask extends EventTask {
	private MessageQueueEventImpl messageQueue;

	public ReceiveEventTask(MessageQueueEventImpl messageQueue, Byte[] msg) {
		this.messageQueue = messageQueue;
		this.runnable = new Runnable() {
			@Override
			public void run() {
				
				messageQueue.listener.received(msg);
			}
		};
	}
}
