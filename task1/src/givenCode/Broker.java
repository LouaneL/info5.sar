package givenCode;

import task1.impl.ChannelImpl;

public abstract class Broker {
	public Broker() {}
    public Broker(String name) {}
    public abstract ChannelImpl accept(int port);
    public abstract ChannelImpl connect(String name, int port);
}
