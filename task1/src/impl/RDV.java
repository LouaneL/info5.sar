package impl;

import givenCode.CircularBuffer;

public class RDV {
	int port = -1;
	BrokerImpl acceptBroker;
	BrokerImpl connectBroker;
	ChannelImpl connectChannel;
	ChannelImpl acceptChannel;
	CircularBuffer circularBuffer1;
	CircularBuffer circularBuffer2;

	public RDV(int port) {
		this.port = port;
	}

	public ChannelImpl accept(BrokerImpl broker, int port) {
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

	public ChannelImpl connect(BrokerImpl broker, String name, int port) {
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
	
	public void createChannel() {
		circularBuffer1 = new CircularBuffer(512);
		circularBuffer2 = new CircularBuffer(512);
		connectChannel = new ChannelImpl(circularBuffer1, circularBuffer2);
		acceptChannel = new ChannelImpl(circularBuffer2, circularBuffer1);
	}
}
