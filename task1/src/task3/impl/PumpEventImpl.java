package task3.impl;

import java.util.LinkedList;
import java.util.List;

import task1.impl.BrokerManager;
import task3.givenCode.IEvent;
import task3.givenCode.PumpEvent;

public class PumpEventImpl extends PumpEvent {
	static private PumpEventImpl instance;
	LinkedList<IEvent> events;
	Boolean isKilled = false;
	
	private PumpEventImpl() {
		events = new LinkedList<IEvent>();
	}

	public static PumpEventImpl getInstance( ) {
		if (instance == null) {
			instance = new PumpEventImpl();
		}
		return instance;
	}
	
	@Override
	public void post(IEvent e) {
		events.add(e);
		notify();
	}

	@Override
	public void start() {
		while (!isKilled) {
			while (!events.isEmpty()) {
				Event event = (Event) events.remove();
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
