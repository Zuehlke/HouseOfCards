# Protocol for Bots

This chapter describes the protocol which is used in the communication between the **server** and **bot**.
Messages from both sides send its messages to specific REST endpoints at the other end.

Note that for communication we use HTTP. This means that endpoints currently ignore the HTTP Response since we're
only using the Request for transferring messages.
