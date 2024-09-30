package task2.givencode;

import givenCode.Broker;
import givenCode.Task;
import task1.impl.BrokerImpl;

public abstract class TaskQueue extends Thread {

	public TaskQueue() {}

	public TaskQueue(Broker broker, Runnable runnable) {}

	public TaskQueue(QueueBroker QueuBroker, Runnable runnable) {}

	public abstract BrokerImpl getBroker();

	public abstract QueueBroker getQueueBroker();

}
