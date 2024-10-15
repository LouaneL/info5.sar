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

	public static PumpEventImpl getInstance( ) {
		if (instance == null) {
			instance = new PumpEventImpl();
		}
		return instance;
	}
	
	@Override
	public synchronized void post(EventTask event) {
		runnables.add(event);
		notify();
	}

	@Override
	public synchronized void start() {
		while (!isKilled) {
			System.out.println("In start PumpEvent");
			while (!runnables.isEmpty()) {
				EventTask event = runnables.remove();
				System.out.println("Event react");
				event.react();	
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
