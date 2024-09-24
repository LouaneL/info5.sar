package impl;

public class Server implements Runnable{
	BrokerImpl broker;
	
	public Server(BrokerImpl broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		ChannelImpl channel = broker.accept(80);
		
		Byte[] msg = new Byte[10];
		
		channel.read(msg, 0, 10);
		channel.write(msg, 0, 10);
	}
	
}
