# **Specification**

We want to implement *message queues* with **events**. This process aims to write/read a block of bytes instead of byte by byte, it's a *messages flow*.
It's a layer above the channels and brokers.
<br>
<br>

## QueueBrokerEvent
QueueBrokerEvent has two interfaces, one for an accept listener and one for a connect listener.

bind -> aims to open this QueueBroker for connexions.
<br>
unbind -> aims to close the QueueBroker for connexions.

connect -> aims to connect the QueueBroker with the one in the parameters.

### **AcceptListener**
accepted -> if a QueueBroker connect to this QueueBroker then this method is called.

### **ConnectedListener**
connected -> if the connexion of QueueBroker is accepted then this method is called.
<br>
refused -> if the connexion of QueueBroker isn't accepted then this method is called.
<br>
<br>

## MessageQueueEvent
MessageQueue has one interface, a listener to receive and send 
messages.

setListener -> aims to add a listener to the class.

send -> aims to send a message with channels.
close -> aims to close the channels, so messages can't be sent anymore.
closed -> to know if the MessageQueue is closed.
<br>
<br>

## TaskEvent
A TaskEvent has one runnable.
