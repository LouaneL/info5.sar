package task3.impl;

import task3.givenCode.IEvent;

public class EventTask implements IEvent {
	Runnable runnable;
	boolean isKilled;
	private static EventTask task;

	public EventTask() {}
	
	public EventTask(Runnable runnable) {
		this.runnable = runnable;
	}
	
	public synchronized void post(Runnable r) {
        if (!isKilled) {
            this.runnable = r;
            PumpEventImpl.getInstance().post(this);
        } else {
            throw new IllegalStateException("Task is dead");
        }
    }

    public synchronized void postTask() {
        if (isKilled) {
        	PumpEventImpl.getInstance().post(this);
        } else {
            throw new IllegalStateException("Task is dead");
        }
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

	public static EventTask getTask() {
		return task;
	}

	public void kill() {
		this.isKilled = true;
	}

	public boolean killed() {
		return this.isKilled;
	}

	@Override
	public void react() {
		runnable.run();
	}
}
