package impl;

public class echoServer {
	
	public static void main(String[] args) throws InterruptedException {
		BrokerManager brokerManager = new BrokerManager();
		
		BrokerImpl clientBroker = new BrokerImpl("clientBroker", brokerManager);
		BrokerImpl serverBroker = new BrokerImpl("serverBroker", brokerManager);
		
		Client clientRunnable = new Client(clientBroker);
		Server serverRunnable = new Server(serverBroker);
		
		TaskImpl client = new TaskImpl(clientBroker, clientRunnable);
		TaskImpl server = new TaskImpl(serverBroker, serverRunnable);
		
		server.join();
		client.join();
	}
}
