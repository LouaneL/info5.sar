package task3.impl;

import java.util.LinkedList;
import java.util.List;

import task1.impl.BrokerManager;
import task3.givenCode.IEvent;
import task3.givenCode.PumpEvent;

public class PumpEventImpl extends PumpEvent {
	static private PumpEventImpl instance;
	LinkedList<EventTask> runnables;
	Boolean isKilled = false;

	private PumpEventImpl() {
		runnables = new LinkedList<EventTask>();
	}

	public static PumpEventImpl getInstance() {
		if (instance == null) {
			instance = new PumpEventImpl();
		}
		return instance;
	}

	@Override
	public synchronized void post(EventTask event) {
		runnables.addLast(event);
		notify();
	}

	@Override
	public synchronized void start() {
		while (!isKilled) {
			if (!runnables.isEmpty()) {
				EventTask event = runnables.removeFirst();
				event.react();
				event.kill();
			} else {
				try {
					wait();
				} catch (InterruptedException e) {
					// Nothing to do
				}
			}
		}

	}

	@Override
	public void kill() {
		Thread.currentThread().interrupt();
		isKilled = true;
	}

}
