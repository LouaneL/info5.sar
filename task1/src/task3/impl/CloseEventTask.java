package task3.impl;

public class CloseEventTask extends EventTask {
	private MessageQueueEventImpl messageQueue;

	public CloseEventTask(MessageQueueEventImpl messageQueue) {
		this.messageQueue = messageQueue;
		this.runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("In CloseEventTask");
				messageQueue.getChannel().disconnect();
				messageQueue.isClosed = true;
			}
		};
	}
}
