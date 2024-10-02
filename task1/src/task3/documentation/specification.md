# **Specification**

We want to implement *message queues* with **events**. This process aims to write/read a block of bytes instead of byte by byte, it's a *messages flow*.
It's a layer above the channels and brokers.

## QueueBrokerEvent
QueueBrokerEvent has two interfaces, one for an accept listener and one for a connect listener.
<br>
<br>

## MessageQueueEvent