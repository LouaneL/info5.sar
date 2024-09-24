package impl;

public class Client implements Runnable{
	BrokerImpl broker;
	
	public Client(BrokerImpl broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		ChannelImpl channel = broker.connect(broker.getName(), 80);
		Byte[] msgDebut = new Byte[10];
		Byte[] msgFin = new Byte[10];
		
		for(int i=0; i<10; i++) {
			msgDebut[i] = (byte) i;
		}
		
		channel.write(msgDebut, 0, 10);
		channel.read(msgFin, 0, 10);
		
		if(msgDebut == msgFin) {
			System.out.println("TEST PASSED");
		} else {
			System.out.println("TEST FAILED");
		}
	}

}
