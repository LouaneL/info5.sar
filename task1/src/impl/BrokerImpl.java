package impl;

import java.util.HashMap;
import java.util.Map;

public class BrokerImpl {
	BrokerManager brokerManager;
	String name;
	Map<Integer, RDV> RDVs;

	public BrokerImpl(String name, BrokerManager brokerManager) {
		this.name = name;
		this.brokerManager = brokerManager;
		brokerManager.addBroker(this);
		RDVs = new HashMap<Integer, RDV>();
	}

	/*
	 * Pb si le rdv existe déjà avec le connect de l'autre broker
	 */
	public synchronized ChannelImpl accept(int port) {
		RDV rdv = null;
		if (getRDV(port, "accept") != null) {
			throw new IllegalStateException("An accept already exists in this port");
		}
		rdv = getRDV(port, "connect");
		if (rdv == null) {
			System.out.println("Creation of an accept RDV");
			rdv = new RDV(port, "accept");
			addRDV(rdv);
		} else {
			removeRDV(rdv);
		}
		return rdv.accept(this, port);
	}

	public synchronized ChannelImpl connect(String name, int port) {
		BrokerImpl connectedBroker = brokerManager.getBroker(name);
		RDV rdv = connectedBroker.getRDV(port, "accept");
		if (rdv == null) {
			System.out.println("Creation of a connect RDV");
			rdv = new RDV(port, "connect");
			connectedBroker.addRDV(rdv);
		} else {
			removeRDV(rdv);
		}
		return rdv.connect(this, name, port);
	}

	public String getName() {
		return this.name;
	}

	public RDV getRDV(int port, String type) {
		RDV rdv = RDVs.get(port);
		if (rdv != null && rdv.getType() == type) {
			return rdv;
		}
		return null;
	}

	public void addRDV(RDV rdv) {
		RDVs.put(rdv.getPort(), rdv);
	}

	public RDV removeRDV(RDV rdv) {
		return RDVs.remove(rdv.getPort());
	}
}
