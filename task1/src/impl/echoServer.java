package impl;

public class echoServer {
	
	public static void main(String[] args) throws InterruptedException {
		BrokerManager brokerManager = BrokerManager.getInstance();
		
		BrokerImpl clientBroker = new BrokerImpl("clientBroker", brokerManager);
		BrokerImpl serverBroker = new BrokerImpl("serverBroker", brokerManager);
		
		Client clientRunnable = new Client(clientBroker);
		Server serverRunnable = new Server(serverBroker);
		
		TaskImpl server = new TaskImpl(serverBroker, serverRunnable);
		TaskImpl client = new TaskImpl(clientBroker, clientRunnable);
		
		client.join();
		server.join();
		
	}
}
