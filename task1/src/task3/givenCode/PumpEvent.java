package task3.givenCode;

public abstract class PumpEvent extends Thread {
	public abstract void post(Runnable runnable);

	public abstract void start();

	public abstract void kill();
}
