package impl;
import java.util.Map;


public class BrokerImpl {
	BrokerManager brokerManager;
	String name;
	Map<Integer, RDV> RDVs;

	public BrokerImpl(String name, BrokerManager brokerManager) {
		this.name = name;
		this.brokerManager = brokerManager;
		brokerManager.addBroker(this);
	}
/*
 * Pb si le rdv existe déjà avec le connect de l'autre broker
 */
	public ChannelImpl accept(int port) {
		RDV rdv = getRDV(port);
		if (rdv == null) {
			rdv = new RDV(port);
			addRDV(rdv);
		}
		return rdv.accept(this, port);
	}
	
	public ChannelImpl connect(String name, int port) {
		BrokerImpl connectedBroker = brokerManager.getBroker(name);
		RDV rdv = connectedBroker.getRDV(port);
		if (rdv == null) {
			rdv = new RDV(port);
		}
		addRDV(rdv);
		return rdv.connect(this, name, port);
	}
	
	public String getName() {
		return this.name;
	}
	
	public RDV getRDV(int port) {
		return RDVs.get(port);
	}
	
	public void addRDV(RDV rdv) {
		RDVs.put(rdv.getPort(), rdv);
	}
	
	public RDV removeRDV(RDV rdv) {
		return RDVs.remove(rdv.getPort());
	}
}
