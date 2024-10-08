package task3.givenCode;

public class Message {
	Byte[] bytes;
	int offset;
	int length;
	
	public Message(Byte[] bytes, int offset, int length) {
		this.bytes = bytes;
		this.offset = offset;
		this.length = length;
	}
}
