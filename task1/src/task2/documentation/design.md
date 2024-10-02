# **Design**

## Runnable

As the implementation for channel, two runnables are created. One for the client, ClientQueue and one for the server, ServerQueue.

The client side makes a connect on the QueueBroker to have the MessageQueue and then the communication with the server starts.
And one the server side, a accept is made on the QueueBroker to have the MessageQueue.
<br>
<br>

## MessageQueue

On the send method all the bytes must be sent, so while all the bytes arn't sent the MessageQueue will try to write them on the channel. A variable will save the number of bytes sent by the channel.
<br>
<br>

## QueueBroker

The connect and accept methods call the QueueBroker broker ones methods and create a new MessageQueue with the channel returned by the broker.
<br>
<br>

## TaskQueue

This class is design as the same as the Task class but there is the possibility to have a QueueBroker instead of a Borker.