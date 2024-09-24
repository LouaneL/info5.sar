# **Design**

## Channel

We need to create two channels to create a communication between two tasks. For example, we have task1 and task2. One channel is used by task1 to read and task2 to write and the other one is used by task2 to read and task1 to write. With this process, we avoid the two tasks to write at the same time and mix their messages.
The server and client channel need to know each other, so one channel is created with an "in" CircularBuffer and a "out" CircularBuffer. The two channel have the same CircularBuffer at inverse.

If one channel is disconnected t
<br>
<br>

## accept, connect and RDV

To create a connection between two brokers, they need a connect and an accept. They don't have a priority, they just wait for both of them to be sent, it's called a rendez-vous. So, an object RDV will be created between the two brokers.

This object contains the two brokers, the connection port, the type (which method has created this rdv) and two methods *accept* and *connect*, which return a channel. These two functions are called by the brokers, with this process the RDV can know the two brokers.
If a RDV is created and only have a accept or a connect, it sleep to wait the other one.

There is a method to create the two channels. These channels need to be link, so two CircularBuffer are created to be the in/out or out/in of these channels.
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
<br>
The client runnable contains the connect and some instructions to send messages to the server.
<br>
The server runnable contains the acccept and some instructions.
<br>
Thanks 
<br>
<br>

## Broker

A broker has a map of RDVs, these RDVs are only stock in a broker server.
The accept and connect methods create a RDV in the server broker, if it's the first method to be called. If a client makes a connect on port 80 and  the server makes an accept on port 80, then a RDV will be created by the connect, because the accept will search in the broker RDVs map the matching RDV for his connection.
<br>
The accept and connect methods of broker call the ones of the rdv.
<br>
<br>

## Task

A task contains a runnable, it's run in the task run method.