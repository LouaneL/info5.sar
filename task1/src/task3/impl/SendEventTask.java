package task3.impl;

import task3.givenCode.Message;

public class SendEventTask extends EventTask{
	private MessageQueueEventImpl messageQueue;

	public SendEventTask(MessageQueueEventImpl messageQueue, Message message) {
		this.messageQueue = messageQueue;
		this.runnable = new Runnable() {
			@Override
			public void run() {
				messageQueue.listener.sent(message);
			}
		};
	}
}
