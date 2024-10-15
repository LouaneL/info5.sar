package task3.impl;

import task3.givenCode.QueueBrokerEvent.IAcceptListener;

public class AcceptEventTask extends EventTask{

	public AcceptEventTask(IAcceptListener listener, MessageQueueEventImpl messageQueue) {
		this.runnable = new Runnable() {
			@Override
			public void run() {
				listener.accepted(messageQueue);
			}
		};
	}
}
