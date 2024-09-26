package impl;

import givenCode.CircularBuffer;

public class RDV {
	int port = -1;
	
	String type;
	
	BrokerImpl acceptBroker;
	BrokerImpl connectBroker;
	
	ChannelImpl connectChannel;
	ChannelImpl acceptChannel;

	public RDV(int port, String type) {
		this.port = port;
		this.type = type;
	}

	public synchronized ChannelImpl accept(BrokerImpl broker, int port) {
		this.acceptBroker = broker;
		System.out.println("Accept to broker: " + acceptBroker.getName());
		
		if (connectBroker != null) {
			createChannel();
			notify();
		} else {
			while (connectBroker == null) {
				try {
					System.out.println("In accept wait loop");
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
		
		return acceptChannel;
	}

	public synchronized ChannelImpl connect(BrokerImpl broker, String name, int port) {
		this.connectBroker = broker;
		System.out.println("Connecting to broker: " + name);

		if (acceptBroker != null) {
			createChannel();
			notify();
		} else {
			while (acceptBroker == null) {
				System.out.println("In connect wait loop");
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
		
		return connectChannel;
	}

	public int getPort() {
		return port;
	}
	
	public String getType() {
		return type;
	}
	
	public void createChannel() {
		CircularBuffer circularBuffer1 = new CircularBuffer(512);
		CircularBuffer circularBuffer2 = new CircularBuffer(512);
		connectChannel = new ChannelImpl(circularBuffer1, circularBuffer2);
		acceptChannel = new ChannelImpl(circularBuffer2, circularBuffer1);
		connectChannel.setLinkedChannel(acceptChannel);
		acceptChannel.setLinkedChannel(connectChannel);
	}
	
}
