package com.example.kotlin_example_app

import com.example.kotlin_example_app.ArticleServiceGrpc.getServiceDescriptor
import com.google.protobuf.Empty
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Holder for Kotlin coroutine-based client and server APIs for
 * com.example.kotlin_example_app.ArticleService.
 */
object ArticleServiceGrpcKt {
  const val SERVICE_NAME: String = ArticleServiceGrpc.SERVICE_NAME

  @JvmStatic
  val serviceDescriptor: ServiceDescriptor
    get() = ArticleServiceGrpc.getServiceDescriptor()

  val findAllArticlesMethod: MethodDescriptor<Empty, ArticleList>
    @JvmStatic
    get() = ArticleServiceGrpc.getFindAllArticlesMethod()

  /**
   * A stub for issuing RPCs to a(n) com.example.kotlin_example_app.ArticleService service as
   * suspending coroutines.
   */
  @StubFor(ArticleServiceGrpc::class)
  class ArticleServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT
  ) : AbstractCoroutineStub<ArticleServiceCoroutineStub>(channel, callOptions) {
    override fun build(channel: Channel, callOptions: CallOptions): ArticleServiceCoroutineStub =
        ArticleServiceCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @return The single response from the server.
     */
    suspend fun findAllArticles(request: Empty): ArticleList = unaryRpc(
      channel,
      ArticleServiceGrpc.getFindAllArticlesMethod(),
      request,
      callOptions,
      Metadata()
    )}

  /**
   * Skeletal implementation of the com.example.kotlin_example_app.ArticleService service based on
   * Kotlin coroutines.
   */
  abstract class ArticleServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for
     * com.example.kotlin_example_app.ArticleService.FindAllArticles.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    open suspend fun findAllArticles(request: Empty): ArticleList = throw
        StatusException(UNIMPLEMENTED.withDescription("Method com.example.kotlin_example_app.ArticleService.FindAllArticles is unimplemented"))

    final override fun bindService(): ServerServiceDefinition = builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = ArticleServiceGrpc.getFindAllArticlesMethod(),
      implementation = ::findAllArticles
    )).build()
  }
}
