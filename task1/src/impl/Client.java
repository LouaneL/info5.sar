package impl;

public class Client implements Runnable{
	BrokerImpl broker;
	
	public Client(BrokerImpl broker) {
		this.broker = broker;
	}

	@Override
	public void run() {
		ChannelImpl channel = broker.connect("serverBroker", 80);
		Byte[] msgDebut = new Byte[10];
		Byte[] msgFin = new Byte[10];
		
		for(int i=0; i<10; i++) {
			msgDebut[i] = (byte) i;
		}
		
		channel.write(msgDebut, 0, 10);
		channel.read(msgFin, 0, 10);
		channel.disconnect();
		
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
