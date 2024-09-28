package task1.impl;

import givenCode.Task;

public class TaskImpl extends Task {
	BrokerImpl broker;
	Runnable runnable;
	

	TaskImpl(BrokerImpl broker, Runnable runnable) {
		this.broker = broker;
		this.runnable = runnable;
		this.start();
	}

	public BrokerImpl getBroker() {
		return this.broker;
	}
	
	public void run() {
		runnable.run();
	}

}
