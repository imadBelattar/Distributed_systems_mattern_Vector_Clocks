# Mattern Vector Clock Implementation in Distributed Systems

## Introduction
This repository contains a project that demonstrates the implementation of the **Mattern vector clock** algorithm in a distributed system environment. The project utilizes **Docker** to simulate a multi-node distributed system, where each node tracks event ordering and causal relationships using vector clocks. The communication between the nodes is established using **Java sockets** and **threads** to mimic message passing.

---

## Project Overview
- **Objective**: Implement the Mattern vector clock to track event order in a distributed system.
- **Technology Stack**:
  - **Docker**: To create and manage the containers representing system nodes.
  - **Java Sockets**: For establishing communication between nodes.
  - **Java Threads**: For parallel execution and handling messages.

---

## Project Explanation

- **Node Representation**:
  - Each node is represented as a container running its own process.
  - Nodes are capable of sending and receiving messages from other nodes.

- **Communication**:
  - Java sockets are used to send and receive messages between the nodes.
  - Each node maintains its own vector clock to keep track of the message's causal order.

- **Message Passing**:
  - When a node sends a message, it increments its local vector clock.
  - Upon receiving a message, the recipient node updates its vector clock based on the incoming message.

- **Tracking Event Order**:
  - The vector clock allows nodes to determine whether an event happened before, after, or concurrently with another event.

- **Docker Containers**:
  - Three containers are used to simulate the nodes in the distributed system.
  - Each container runs its own instance of the node, communicating with others to synchronize the vector clock.

---

This project serves as a basic implementation of **time synchronization** and **event ordering** in distributed systems using vector clocks.
