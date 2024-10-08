package task3.impl;

import java.util.LinkedList;
import java.util.List;

import task1.impl.BrokerManager;
import task3.givenCode.IEvent;
import task3.givenCode.PumpEvent;

public class PumpEventImpl extends PumpEvent {
	static private PumpEventImpl instance;
	LinkedList<Runnable> runnables;
	Boolean isKilled = false;
	
	private PumpEventImpl() {
		runnables = new LinkedList<Runnable>();
	}

	public static PumpEventImpl getInstance( ) {
		if (instance == null) {
			instance = new PumpEventImpl();
		}
		return instance;
	}
	
	@Override
	public void post(Runnable runnable) {
		runnables.add(runnable);
		notify();
	}

	@Override
	public void start() {
		while (!isKilled) {
			while (!runnables.isEmpty()) {
				Runnable runnable = runnables.remove();
				runnable.run();
			}
			try {
				wait();
			} catch (InterruptedException e) {
				// Nothing to do
			}
		}
	}

	@Override
	public void kill() {
		isKilled = true;
	}

}
