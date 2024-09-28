# **Specification**

We want to implement *message queues*. This process aims to write/read a block of bytes instead of byte by byte, it's a *messages flow*.
It's a layer above the channels and brokers.

<br>

## Task
A task has a queue broker and a runnable. It has a method getQueueBroker(). 
Between two tasks, there is a MessageQueue. So, a task need a map of all its MessageQueue, for all its connexion with tasks.
<br>
<br>

## QueueBroker
A QueueBroker has a broker. It has an accept and connect methods that 

This class instantiate a broker for a task. It's a layer above Broker.
The accept and connect methods have the same goal than the ones of Broker. QueueBroker can connect to another QueueBroker to start creating a communication between them. The client side has a MessageQueue thanks to connect() and the server side has a MessageQueue thanks to accept().
<br>
<br>

## MessageQueue
A MessageQueue has a QueueBroker and a Channel. The QueueBroker is the object which created the MessageQueue and the channel is the object to read and write the messages.

It's a layer above Channel.

The send method use the Channel write method and receive method use the Channel read method.

The closed method close the the conenction between two QueueBroker, it use the Channel methods.

The close method aims to know if the MessageQueue is closed