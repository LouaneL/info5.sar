package task3.impl;

import task3.givenCode.IEvent;

public class Event implements IEvent {
	QueueBrokerEventImpl queueBroker;
	Runnable runnable;
	
	public Event(QueueBrokerEventImpl queueBroker, Runnable runnable) {
		this.queueBroker = queueBroker;
		this.runnable = runnable;
	}
	
	@Override
	public void react() {
		runnable.run();
	}
}
