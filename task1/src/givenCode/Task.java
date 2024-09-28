package givenCode;

import task1.impl.BrokerImpl;

public abstract class Task extends Thread {
	public Task() {}

	public Task(Broker broker, Runnable runnable) {}

	public abstract BrokerImpl getBroker();
}
