package impl;

import java.util.Map;

public class Server implements Runnable{
	BrokerImpl broker;
	
	// Soit on fait une map de pairs de channels ou de clients
	ChannelImpl inChannel;
	ChannelImpl outChannel;
	Map<String, Client> clients;
	
	public Server(BrokerImpl broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		this.outChannel = broker.accept(0);
		
	}
	
	public int write(Byte[] bytes, int offset, int length) {
		return outChannel.write(bytes, offset, length);
	}
	
	public int read(Byte[] bytes, int offset, int length) {
		return inChannel.read(bytes, offset, length);
	}
	
}
