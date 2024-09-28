package task1.impl;

import java.util.HashMap;
import java.util.Map;

public class BrokerImpl {
	BrokerManager brokerManager;
	String name;
	HashMap<Integer, RDV> RDVs;

	public BrokerImpl(String name, BrokerManager brokerManager) {
		this.name = name;
		this.brokerManager = brokerManager;
		brokerManager.addBroker(this);
		RDVs = new HashMap<Integer, RDV>();
	}


	public ChannelImpl accept(int port) {
		RDV rdv = null;
		synchronized (RDVs) {
			if (getRDV(port) != null) {
				throw new IllegalStateException("An accept already exists in the port "+port);
			}
			rdv = new RDV(port);
			addRDV(rdv);

			RDVs.notifyAll();
		}
		return rdv.accept(this, port);
	}

	public ChannelImpl _connect(BrokerImpl broker, int port) {
		RDV rdv = null;

		synchronized (RDVs) {
			rdv = getRDV(port);

			while (rdv == null) {
				try {
					RDVs.wait();
				} catch (InterruptedException ie) {
					// Noting to do
				}
				rdv = getRDV(port);
			}
			removeRDV(rdv);
		}
		return rdv.connect(broker, name, port);
	}

	public synchronized ChannelImpl connect(String name, int port) {
		BrokerImpl acceptBroker = brokerManager.getBroker(name);

		if (acceptBroker == null) {
			throw new IllegalStateException("The broker " + name + " doesn't exist");
		}
		return acceptBroker._connect(this, port);
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
