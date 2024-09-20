# **Design**

## Channel

We need to create two channels to create a communication between two tasks. For example, we have task1 and task2. One channel is used by task1 to read and task2 to write and the other one is used by task2 to read and task1 to write. With this process, we avoid the two tasks to write at the same time and mix their messages.

If one channel is disconnected t
<br>
<br>

## accept, connect and RDV

To create a connection between two brokers, they need a connect and an accept. They don't have a priority, they just wait for both of them to be sent, it's called a rendez-vous. So, an object RDV will be created between the two brokers.

This object contains the two brokers, the connection port and two methods *accept* and *connect*, which return a channel. These two functions are called the brokers, with this process the RDV can know the two brokers. The brokers have a RDV map.

If a RDV is created and only have a accept or a connect, it sleep to wait the other one.
<br>
<br>

## BrokerManager

The object BrokerManager contains all the created brokers. It's an easier way to find a broker.

The BrokerManager has methods to add, remove or find a broker, maybe other if it's needed. It also has a Broker array.

Each broker contains the object BrokerManager.
<br>
<br>

## Runnable client and server

To create a task a runnable is needed. It exist two task types: client and server, so a runnable is implemented for each of them.
