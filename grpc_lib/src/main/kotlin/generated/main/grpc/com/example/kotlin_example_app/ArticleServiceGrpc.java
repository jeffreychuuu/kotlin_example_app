package com.example.kotlin_example_app;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: article.proto")
public final class ArticleServiceGrpc {

  private ArticleServiceGrpc() {}

  public static final String SERVICE_NAME = "com.example.kotlin_example_app.ArticleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.example.kotlin_example_app.ArticleList> getFindAllArticlesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindAllArticles",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.example.kotlin_example_app.ArticleList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.example.kotlin_example_app.ArticleList> getFindAllArticlesMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.example.kotlin_example_app.ArticleList> getFindAllArticlesMethod;
    if ((getFindAllArticlesMethod = ArticleServiceGrpc.getFindAllArticlesMethod) == null) {
      synchronized (ArticleServiceGrpc.class) {
        if ((getFindAllArticlesMethod = ArticleServiceGrpc.getFindAllArticlesMethod) == null) {
          ArticleServiceGrpc.getFindAllArticlesMethod = getFindAllArticlesMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.example.kotlin_example_app.ArticleList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindAllArticles"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.kotlin_example_app.ArticleList.getDefaultInstance()))
              .setSchemaDescriptor(new ArticleServiceMethodDescriptorSupplier("FindAllArticles"))
              .build();
        }
      }
    }
    return getFindAllArticlesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ArticleServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ArticleServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ArticleServiceStub>() {
        @java.lang.Override
        public ArticleServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ArticleServiceStub(channel, callOptions);
        }
      };
    return ArticleServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ArticleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ArticleServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ArticleServiceBlockingStub>() {
        @java.lang.Override
        public ArticleServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ArticleServiceBlockingStub(channel, callOptions);
        }
      };
    return ArticleServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ArticleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ArticleServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ArticleServiceFutureStub>() {
        @java.lang.Override
        public ArticleServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ArticleServiceFutureStub(channel, callOptions);
        }
      };
    return ArticleServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ArticleServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void findAllArticles(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.example.kotlin_example_app.ArticleList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindAllArticlesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFindAllArticlesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                com.example.kotlin_example_app.ArticleList>(
                  this, METHODID_FIND_ALL_ARTICLES)))
          .build();
    }
  }

  /**
   */
  public static final class ArticleServiceStub extends io.grpc.stub.AbstractAsyncStub<ArticleServiceStub> {
    private ArticleServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ArticleServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ArticleServiceStub(channel, callOptions);
    }

    /**
     */
    public void findAllArticles(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.example.kotlin_example_app.ArticleList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindAllArticlesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ArticleServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ArticleServiceBlockingStub> {
    private ArticleServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ArticleServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ArticleServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.kotlin_example_app.ArticleList findAllArticles(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindAllArticlesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ArticleServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ArticleServiceFutureStub> {
    private ArticleServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ArticleServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ArticleServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.kotlin_example_app.ArticleList> findAllArticles(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindAllArticlesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND_ALL_ARTICLES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ArticleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ArticleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_ALL_ARTICLES:
          serviceImpl.findAllArticles((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.example.kotlin_example_app.ArticleList>) responseObserver);
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

  private static abstract class ArticleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ArticleServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.kotlin_example_app.ArticleOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ArticleService");
    }
  }

  private static final class ArticleServiceFileDescriptorSupplier
      extends ArticleServiceBaseDescriptorSupplier {
    ArticleServiceFileDescriptorSupplier() {}
  }

  private static final class ArticleServiceMethodDescriptorSupplier
      extends ArticleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ArticleServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ArticleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ArticleServiceFileDescriptorSupplier())
              .addMethod(getFindAllArticlesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
