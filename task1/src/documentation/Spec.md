# **Specification**

    This task aims to create a communication between a server and tasks, thanks to a channel.
    a task will send some bytes to the server and the server will send back the same bytes (the sever is like an echo). Then the client will disconnect itself to the server.

<br>

A channel is a link between several entities to comunicate between each others. A channel is created by a broker. A channel will use sockets for the entities to read and write.<br>
The channels are fifo looseless, fullduplex and a byte flow.<br>
A channel is for one task only, because there will be some problems with read and write, even if they are synchronized methods.

Each entities have a broker, it has an unique name and a port. A broker can handle multiple tasks
When a task is created it return the broker associated with.


