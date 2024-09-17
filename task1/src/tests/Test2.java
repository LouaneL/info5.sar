package tests;

import givenCode.Broker;
import givenCode.Channel;
import givenCode.CircularBuffer;
import givenCode.Task;

public class Test2 {
	
	public static void main(String args[]) {
		Broker broker = new Broker("broker");
		EchoServer echoServer = new EchoServer();

		Task task1 = new Task(broker, this);
		Channel channel1 = task1.getBroker().accept(80);
		
		int cb1Capacity = 3;
		CircularBuffer cb1 = new CircularBuffer(cb1Capacity);
		cb1.push((byte) 0);
		cb1.push((byte) 1);
		cb1.push((byte) 2);
		
		Task task2 = new Task(broker, this);
		Channel channel2 = task1.getBroker().accept(90);
		
		int cb2Capacity = 5;
		CircularBuffer cb2 = new CircularBuffer(cb2Capacity);
		cb1.push((byte) 3);
		cb1.push((byte) 4);
		cb1.push((byte) 5);
		cb1.push((byte) 6);
		cb1.push((byte) 7);
		
		echoServer.readTask();
		task1.run();
		echoServer.sendBackToTask();
		
		channel1.disconnect();
		
		echoServer.readTask();
		task2.run();
		echoServer.sendBackToTask();
		
		channel2.disconnect();
	}
}
