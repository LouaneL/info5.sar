package impl;

public class Client implements Runnable{
	BrokerImpl broker;
	
	public Client(BrokerImpl broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		ChannelImpl channel = broker.connect(broker.getName(), 0);
	}

}
