# **Specification**

    This task aims to create a communication between a server and several clients.
    A client is created and will be connected to the server.
    This client will send some bytes to the server and the server will send back the same bytes (the sever is an echo). Then the client will disconnect itself to the server.

<br>
A client is created thanks to the server.
The server will create a channel for this client and will send it to the client.
A channel is used for the communication, we can write and read on it.

The client bytes are stored in a circularBuffer. The bytes in the circularBuffer will be write on the channel. Then the server will read it via the channel and will write it in the channel.<br>
When the client receive the response it will be disconnected from the server.