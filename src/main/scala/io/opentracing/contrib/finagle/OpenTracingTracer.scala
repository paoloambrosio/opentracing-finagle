package io.opentracing.contrib.finagle

import com.twitter.finagle.{tracing => finagle}
import io.opentracing
import io.opentracing.util.GlobalTracer

import scala.collection.concurrent.TrieMap

/**
  * OpenTracing tracer for Finagle
  */
class OpenTracingTracer(tracer: opentracing.Tracer) extends finagle.Tracer {

  /**
    * Default constructor for the service loader
    */
  def this() = {
    this(GlobalTracer.get)
  }

  private val DeferSamplingToOpenTracing = Some(true)

  private val spanCache = new TrieMap[finagle.TraceId, opentracing.Span]()

  override def sampleTrace(traceId: finagle.TraceId): Option[Boolean] = DeferSamplingToOpenTracing

  override def record(record: finagle.Record): Unit = {
    import finagle.Annotation._

    val span = spanCache.getOrElseUpdate(record.traceId,
      tracer.buildSpan("")
        .ignoreActiveSpan()
        .withStartTimestamp(record.timestamp.inMicroseconds)
        .start()
    )

    record.annotation match {
      case Rpc(name) =>
        span.setOperationName(name)
      case ClientRecv() | ServerSend() =>
        span.finish(record.timestamp.inMicroseconds)
        spanCache.remove(record.traceId, span)
      case _ =>
    }
  }

}
