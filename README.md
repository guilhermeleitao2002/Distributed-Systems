# Distributed Systems - Practical Exercises  

This repository contains five practical exercises related to cryptography and gRPC-based distributed systems. Each exercise is self-contained and explores different aspects of secure communication, cryptographic operations, and remote procedure calls (RPC).  

## Exercises  

### 1. Java Cryptography Examples  

This module contains Java-based cryptographic utilities and tests demonstrating different cryptographic primitives.  

#### Topics Covered  
- Symmetric and asymmetric cryptography  
- Key generation and storage  
- Secure random number generation  
- Cryptographic hash functions and digital signatures  
- XML cryptographic encoding  

#### How to Run  
To execute the default example using Maven:  
```sh
mvn compile exec:java
```  
To list available security providers and algorithms:  
```sh
mvn exec:java -P list-algos
```  
To run all unit tests:  
```sh
mvn test
```  
To run a specific test:  
```sh
mvn test -Dtest=AsymCrypto*#testCipherPrivate*
```  

### 2. gRPC Example  

A simple gRPC example demonstrating service implementation and invocation using protocol buffers.  

#### Modules  
- **contract/** - Defines the gRPC service using protocol buffers  
- **server/** - Implements the service  
- **client/** - Calls the service  

#### How to Run  
1. Create a Python virtual environment and install gRPC packages:  
   ```sh
   python -m venv .venv
   source .venv/bin/activate  # On Linux/macOS
   .venv\Scripts\activate     # On Windows
   python -m pip install grpcio grpcio-tools
   ```  
2. Follow module-specific instructions in the respective README files.  

### 3. gRPC Example with Python Client  

An extension of the previous gRPC example, including a Python client for service invocation.  

#### Modules  
- **contract/** - Defines the gRPC service  
- **server/** - Implements the service  
- **client/** - Calls the service  
- **python_client/** - Python-based client implementation  

#### How to Run  
Follow the same setup as the previous gRPC example. The `python_client` module provides an alternative implementation of the client in Python.  

### 4. gRPC Supplier  

This exercise extends the basic gRPC example, modeling a supplier service with potential enhancements like security protections and additional data in messages.  

#### Modules  
- **contract/** - Defines the gRPC service  
- **server/** - Implements the service  
- **client/** - Calls the service  

#### How to Extend  
- Modify the `.proto` definitions to include additional fields in the messages.  
- Implement authentication and encryption for secure communication.  

### 5. gRPC Tic Tac Toe  

A gRPC-based implementation of the classic Tic Tac Toe game, demonstrating game state synchronization using remote procedure calls.  

#### Modules  
- **contract/** - Defines the gRPC service for game communication  
- **server/** - Implements the game logic and manages players  
- **client/** - Provides an interface for players to make moves  

#### How to Run  
1. Start by defining the contract using protocol buffers.  
2. Implement the server logic to handle player interactions.  
3. Run the client to interact with the game server.
