package givenCode;

public abstract class Channel {
    public abstract int read(Byte[] bytes, int offset, int length);
    public abstract int write(Byte[] bytes, int offset, int length);
    public abstract void disconnect();
    public abstract boolean disconnected();
}
