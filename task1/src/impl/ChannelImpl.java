package impl;

import givenCode.Channel;
import givenCode.CircularBuffer;

public class ChannelImpl extends Channel {
	Boolean isDisconnected = false;
	Boolean isLinkedChannelDisconnected = false;
	ChannelImpl linkedChannel;
	CircularBuffer in;
	CircularBuffer out;

	public ChannelImpl(CircularBuffer in, CircularBuffer out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public int read(Byte[] bytes, int offset, int length) {
		if (disconnected()) {
			return -1;
		}
		int cpt = 0;
		try {
			
			while (cpt == 0) {
				if (in.empty()) {
					synchronized (in) {
						if (disconnected() || isLinkedChannelDisconnected) {
							throw new DisconnectedException();
						}
						try {
							in.wait();
						} catch (InterruptedException ie) {
							// Nothing to do
						}
					}
				}
				while (cpt < length && cpt + offset < bytes.length && !in.empty()) {
					bytes[offset + cpt] = in.pull();
					cpt++;
				}
				
				if (cpt != 0) {
					synchronized(in) {
						in.notify();
					}
				}
			}
			
		} catch (DisconnectedException de) {
			if(!disconnected()) {
				isDisconnected = true;
				synchronized (out) {
					out.notifyAll();
				}
			}
		}
		return cpt;
	}

	@Override
	public synchronized int write(Byte[] bytes, int offset, int length) {
		if (isDisconnected) {
			return -1;
		}
		int cpt = 0;
		
		while (cpt == 0) {
			if (out.full()) {
				synchronized (out) {
					if (disconnected()) {
						try {
						throw new DisconnectedException();
						} catch (DisconnectedException de) {
							// Nothing to do
						}
					}
					try {
						out.wait();
					} catch (InterruptedException ie) {
						// Nothing to do
					}
				}
			}
			while (cpt < length && cpt + offset < bytes.length && !out.full()) {
				out.push(bytes[offset + cpt]);
				cpt++;
			}
			
			if (cpt != 0) {
				synchronized(out) {
					out.notify();
				}
			}
		}
		
		return cpt;
	}

	@Override
	public void disconnect() {
		isDisconnected = true;
		linkedChannel.isDisconnected = true;
	}

	@Override
	public boolean disconnected() {
		return isDisconnected;
	}
	
	public void setLinkedChannel(ChannelImpl channel) {
		this.linkedChannel = channel;		
	}

}
