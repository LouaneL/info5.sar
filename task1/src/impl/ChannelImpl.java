package impl;

import givenCode.Channel;
import givenCode.CircularBuffer;

public class ChannelImpl extends Channel{
	Boolean isDisconnected = false;
	CircularBuffer in;
	CircularBuffer out;
	
	public ChannelImpl(CircularBuffer in, CircularBuffer out) {
		this.in = in;
		this.out = out;
	}
	
	@Override
	public int read(Byte[] bytes, int offset, int length) {
		if (isDisconnected) {
			return -1;
		}
		
		notifyAll();
		
		while (in.empty()) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		
		int cpt = 0;
		while (cpt > length && cpt+offset < bytes.length && !in.empty()) {
			bytes[offset+cpt] = in.pull();
			cpt++;
		}
		return cpt;
	}

	@Override
	public int write(Byte[] bytes, int offset, int length) {
		if (isDisconnected) {
			return -1;
		}
		
		notifyAll();
		
		while (in.full()) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		
		int cpt = 0;
		while (cpt > length && cpt+offset < bytes.length && !out.full()) {
			out.push(bytes[offset+cpt]);
			cpt++;
		}
		return cpt;
	}

	@Override
	public void disconnect() {
		isDisconnected = true;
	}

	@Override
	public boolean disconnected() {
		return isDisconnected;
	}

}
