package task2.impl;

import task1.impl.BrokerImpl;

public class ClientQueue implements Runnable{
	BrokerImpl broker;
	QueueBrokerImpl queueBroker;
	
	public ClientQueue(BrokerImpl broker) {
		this.broker = broker;
	}
	
	public ClientQueue(QueueBrokerImpl queueBroker) {
		this.queueBroker = queueBroker;
	}
	
	@Override
	public void run() {
		MessageQueueImpl messageQueue = queueBroker.connect("serverQueueBroker", 80);
		Byte[] msgDebut = new Byte[10];
		
		for(int i=0; i<10; i++) {
			msgDebut[i] = (byte) i;
		}
		
		messageQueue.send(msgDebut,0,10);
		Byte[] msgFin = messageQueue.received();
		messageQueue.close();
		
		int i=0;
		while (i < 10 && msgDebut[i] == msgFin[i]) {
			i++;
		}
		
		if(i==10) {
			System.out.println("TEST PASSED");
		} else {
			for (int j=0; j<10; j++) {
				System.out.println("Output: "+msgFin[j].intValue()+", Expected: "+msgDebut[j].intValue());
			}
			System.out.println(msgFin);
			System.out.println("TEST FAILED");
		}
	}
}
