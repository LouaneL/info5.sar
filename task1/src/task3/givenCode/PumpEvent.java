package task3.givenCode;

import task3.impl.EventTask;

public abstract class PumpEvent extends Thread {
	public abstract void post(EventTask event);

	public abstract void start();

	public abstract void kill();
}
