package tests;

import givenCode.Broker;
import givenCode.Channel;
import givenCode.CircularBuffer;
import givenCode.Task;

public class Test1 {

	public static void main(String args[]) {
		Broker broker = new Broker("broker");
		EchoServer echoServer = new EchoServer();

		Task task1 = new Task(broker, this);
		Channel channel1 = task1.getBroker().accept(80);
		
		int cb1Capacity = 1;
		CircularBuffer cb1 = new CircularBuffer(cb1Capacity);
		cb1.push((byte) 0);
		
		echoServer.readTask();
		task1.run();
		echoServer.sendBackToTask();
		
		channel1.disconnect();
	}

}
