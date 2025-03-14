package pt.tecnico.ttt;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: namingServer.proto")
public final class NamingServerGrpc {

  private NamingServerGrpc() {}

  public static final String SERVICE_NAME = "pt.tecnico.ttt.NamingServer";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pt.tecnico.ttt.RegisterRequest,
      pt.tecnico.ttt.RegisterResponse> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Register",
      requestType = pt.tecnico.ttt.RegisterRequest.class,
      responseType = pt.tecnico.ttt.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pt.tecnico.ttt.RegisterRequest,
      pt.tecnico.ttt.RegisterResponse> getRegisterMethod() {
    io.grpc.MethodDescriptor<pt.tecnico.ttt.RegisterRequest, pt.tecnico.ttt.RegisterResponse> getRegisterMethod;
    if ((getRegisterMethod = NamingServerGrpc.getRegisterMethod) == null) {
      synchronized (NamingServerGrpc.class) {
        if ((getRegisterMethod = NamingServerGrpc.getRegisterMethod) == null) {
          NamingServerGrpc.getRegisterMethod = getRegisterMethod =
              io.grpc.MethodDescriptor.<pt.tecnico.ttt.RegisterRequest, pt.tecnico.ttt.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.ttt.RegisterRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pt.tecnico.ttt.RegisterResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NamingServerMethodDescriptorSupplier("Register"))
              .build();
        }
      }
    }
    return getRegisterMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NamingServerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NamingServerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NamingServerStub>() {
        @java.lang.Override
        public NamingServerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NamingServerStub(channel, callOptions);
        }
      };
    return NamingServerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NamingServerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NamingServerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NamingServerBlockingStub>() {
        @java.lang.Override
        public NamingServerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NamingServerBlockingStub(channel, callOptions);
        }
      };
    return NamingServerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NamingServerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NamingServerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NamingServerFutureStub>() {
        @java.lang.Override
        public NamingServerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NamingServerFutureStub(channel, callOptions);
        }
      };
    return NamingServerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class NamingServerImplBase implements io.grpc.BindableService {

    /**
     */
    public void register(pt.tecnico.ttt.RegisterRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.ttt.RegisterResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pt.tecnico.ttt.RegisterRequest,
                pt.tecnico.ttt.RegisterResponse>(
                  this, METHODID_REGISTER)))
          .build();
    }
  }

  /**
   */
  public static final class NamingServerStub extends io.grpc.stub.AbstractAsyncStub<NamingServerStub> {
    private NamingServerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NamingServerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NamingServerStub(channel, callOptions);
    }

    /**
     */
    public void register(pt.tecnico.ttt.RegisterRequest request,
        io.grpc.stub.StreamObserver<pt.tecnico.ttt.RegisterResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class NamingServerBlockingStub extends io.grpc.stub.AbstractBlockingStub<NamingServerBlockingStub> {
    private NamingServerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NamingServerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NamingServerBlockingStub(channel, callOptions);
    }

    /**
     */
    public pt.tecnico.ttt.RegisterResponse register(pt.tecnico.ttt.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class NamingServerFutureStub extends io.grpc.stub.AbstractFutureStub<NamingServerFutureStub> {
    private NamingServerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NamingServerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NamingServerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pt.tecnico.ttt.RegisterResponse> register(
        pt.tecnico.ttt.RegisterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NamingServerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NamingServerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER:
          serviceImpl.register((pt.tecnico.ttt.RegisterRequest) request,
              (io.grpc.stub.StreamObserver<pt.tecnico.ttt.RegisterResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class NamingServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NamingServerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pt.tecnico.ttt.NamingServerOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NamingServer");
    }
  }

  private static final class NamingServerFileDescriptorSupplier
      extends NamingServerBaseDescriptorSupplier {
    NamingServerFileDescriptorSupplier() {}
  }

  private static final class NamingServerMethodDescriptorSupplier
      extends NamingServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    NamingServerMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NamingServerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NamingServerFileDescriptorSupplier())
              .addMethod(getRegisterMethod())
              .build();
        }
      }
    }
    return result;
  }
}
