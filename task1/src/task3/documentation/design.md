# **Design**

## QueueBrokerManager
The QueueBrokerManager contains a map with all the QueueBroker. It's needed for the connexion with two QueueBrokers. It has 3 methods:

- add -> add a new QueueBroker to the map
- remove -> remove a existing QueueBroker to the map
- get -> get a existing QueueBroker from the map
<br>
<br>

## BrokerManager
The BrokerManager contains a map with all the Broker. It's needed for the connexion with two Brokers. It has 3 methods:

- add -> add a new Broker to the map
- remove -> remove a existing Broker to the map
- get -> get a existing Broker from the map
<br>
<br>

## QueueBroker
The QueueBrokerServer bind himself to the port it want and create an AcceptListener. The QueueBrokerClient call the connect method with the name of the QueueBrokerServer and create a ConnectListener.
<br>
The two of them wait until they received a notificéation of their created listener. The other one send them a notification thanks to the listener they received by the other QueueBroker.
<br>
If the accept method succeed then the accepted method is called.
If the connect method succeed then the connected method is called and if not then the refused method is called. 

When the QueueBrokerServer doesn't want a connexion anymore it call the unbind method and close its port.

The connect and accept methods call the methods of the Broker associated.

<br>
<br>

## MessageQueue
The MessageQueue has a listener to know when the connexion is closed and when it received a message.

A MessageQueue has the listener of the other MessageQueue of the connexion. They are set at the creation of the MessageQueues, in the QueueBrokers.

When the close method is called then it disconnect the used channel.

The send method use the channel and send the entire message.
<br>
<br>

## PumpEvent
The PumpEvent executes all the Event in its pump. Several Event can be executed at the same time.

In the post method, the react method of the executed Events are called.

There is a loop, *when the EventPump isn't kill yet*, if the pump is empty, it wait, and if it's not then an Event is started.
<br>
When a Event is posted then it have to be started.

The Tasks have to know the pumpEvent 
<br>
<br>

## Event
An Event has a runnable.

react -> aims to run its runnable

An Event is put in the pump by its Task.
<br>
<br>

## Task
A Task has to know the pumpEvent to create an event with its runnable and put it in the pumpEvent.

