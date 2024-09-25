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

		if (connectBroker != null) {
			createChannel();
			notifyAll();
		} else {
			while (connectBroker == null) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}

		return acceptChannel;
	}

	public synchronized ChannelImpl connect(BrokerImpl broker, String name, int port) {
		this.connectBroker = broker;

		if (acceptBroker != null) {
			createChannel();
			notifyAll();
		} else {
			while (acceptBroker == null) {
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
	}
	
}
