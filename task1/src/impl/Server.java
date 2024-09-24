package impl;

public class Server implements Runnable{
	BrokerImpl broker;
	
	public Server(BrokerImpl broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		ChannelImpl channel = broker.accept(0);
	}
	
}
