package task3.impl;

import task3.givenCode.QueueBrokerEvent.IConnectListener;

public class ConnectEventTask extends EventTask{

	public ConnectEventTask(IConnectListener listener, MessageQueueEventImpl messageQueue) {
		this.runnable = new Runnable() {
			@Override
			public void run() {
				listener.connected(messageQueue);
			}
		};
	}
}
