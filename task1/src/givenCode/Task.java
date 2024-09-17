package givenCode;

public abstract class Task extends Thread {
    Task(Broker b, Runnable r){}
    public abstract Broker getBroker();
}
