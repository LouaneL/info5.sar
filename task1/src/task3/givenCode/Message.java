package task3.givenCode;

public class Message {
	public Byte[] bytes;
	public int offset;
	public int length;
	
	public Message(Byte[] bytes, int offset, int length) {
		this.bytes = bytes;
		this.offset = offset;
		this.length = length;
	}
}
