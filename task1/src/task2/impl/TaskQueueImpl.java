package task2.impl;

import givenCode.Broker;
import givenCode.Task;
import task1.impl.BrokerImpl;
import task2.givencode.QueueBroker;
import task2.givencode.TaskQueue;

public class TaskQueueImpl extends TaskQueue{
	BrokerImpl broker;
	QueueBrokerImpl queueBroker;
	Runnable runnable;
	
	public TaskQueueImpl(BrokerImpl broker, Runnable runnable) {
		this.broker = broker;
		this.runnable = runnable;
		this.start();
	}
	
	public TaskQueueImpl(QueueBrokerImpl queueBroker, Runnable runnable) {
		this.queueBroker = queueBroker;
		this.runnable = runnable;
	}

	@Override
	public BrokerImpl getBroker() {
		return broker;
	}

	@Override
	public QueueBroker getQueueBroker() {
		return queueBroker;
	}

//	static Task getTask() {
//		return this;
//	}
	
	public void run() {
		runnable.run();
	}

}
