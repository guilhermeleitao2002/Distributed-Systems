package pt.tecnico.ttt.server;

import io.grpc.stub.StreamObserver;
import static io.grpc.Status.INVALID_ARGUMENT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pt.tecnico.ttt.*;
import pt.tecnico.ttt.NamingServerGrpc.NamingServerImplBase;

public class NamingServerServiceImpl  extends NamingServerGrpc.NamingServerImplBase {

	/** Game implementation. */
	private NamingServer namingServer = new NamingServer();

	@Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		namingServer.register(request.getServiceName(), request.getQualifier(), request.getAddress());

		RegisterResponse response = RegisterResponse.getDefaultInstance();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}
	
	public void lookup(LookupRequest request, StreamObserver<LookupResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		List<ServerEntry> result = namingServer.lookup(request.getServiceName(), request.getQualifier());

		List<Server> servers = result.stream().map(entry -> {return Server.newBuilder().setAddress(entry.getAddress()).setQualifier(entry.getQualifier()).build();}).collect(Collectors.toList());

		LookupResponse response = LookupResponse.newBuilder().addAllServers(servers).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	public void delete(DeleteRequest request, StreamObserver<DeleteResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		namingServer.delete(request.getServiceName(), request.getAddress());

		DeleteResponse response = DeleteResponse.getDefaultInstance();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

}
