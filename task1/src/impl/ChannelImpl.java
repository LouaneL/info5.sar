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
		int lengthToRead = length;
		while (lengthToRead > 0) {
			
		}
		return 0;
	}

	@Override
	public int write(Byte[] bytes, int offset, int length) {
		if (isDisconnected) {
			return -1;
		}
		return 0;
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
