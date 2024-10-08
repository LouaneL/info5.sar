package task3.givenCode;

public abstract class PumpEvent extends Thread {
	public abstract void post(IEvent e);

	public abstract void start();

	public abstract void kill();
}
