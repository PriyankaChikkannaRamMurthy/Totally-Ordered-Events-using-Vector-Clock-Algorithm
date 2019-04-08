# Totally-Ordered-Events-using-Vector-Clock-Algorithms

Implementation, Learnings and Issues:

A vector clock is an algorithm which follows a partial ordering of events in a distributed system. Like in Lamport’s timestamps, messages between the processes contain the state of the sending process's logical clock. A vector clock of a system of N processes is an array/vector of N logical clocks, one clock per process; a local "smallest possible values" copy of the global clock-array is kept in each process, with the following rules for clock updates:

*	All clocks are zero initially.
*	Every time there is an internal event in a process, it increments its own logical clock in the vector by one.
*	Every time a process sends a message, it increments its own logical clock in the vector by one and then sends a copy of its own vector.
*	Every time a process receives a message, it increments its own logical clock in the vector by one and updates each element in its vector by taking the maximum of the value in its own vector clock and the value in the vector in the received message (for every element).
 
Was able to grasp the concept of vector clocks. Implementing the timestamps and passing them across the processes was a very difficult issue we faced.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Importing project into workspace:

1. Open Eclipse IDE

2. Right click on "Package Explorer".

3. Select "Import..."

4. Click on "General" and then "Existing Projects into Workspace"

5. Enter the location of the project into root directory.

6. Check "Copy contents into workspace".

7. Click "Finish" to complete the process.

Later,

* Open the projects in the eclipse IDE.
*	Run each of the Java files one after the other.
*	After all three are running, press enter in each of the three consoles to start the multicasting.
* Build All JAVA files and Run them as “Java Applications”

Node1

Node2

Node3

Node4

In 4 different consoles to see the output while they are multicasting the messages with Vector clock implementation.

