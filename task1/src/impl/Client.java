package impl;

public class Client implements Runnable{
	BrokerImpl broker;
	ChannelImpl Channel;
	
	public Client(BrokerImpl broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		this.Channel = broker.connect(broker.getName(), 0);
	}
	
	public int write(Byte[] bytes, int offset, int length) {
		return Channel.write(bytes, offset, length);
	}
	
	public int read(Byte[] bytes, int offset, int length) {
		return Channel.read(bytes, offset, length);
	}
}
