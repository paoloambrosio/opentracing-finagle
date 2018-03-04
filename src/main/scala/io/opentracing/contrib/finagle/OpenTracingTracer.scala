package io.opentracing.contrib.finagle

import com.twitter.finagle.{tracing => finagle}
import io.opentracing
import io.opentracing.util.GlobalTracer

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

  override def sampleTrace(traceId: finagle.TraceId): Option[Boolean] = DeferSamplingToOpenTracing

  override def record(record: finagle.Record): Unit = record match {
    case finagle.Record(traceId, timestamp, finagle.Annotation.Rpc(name), Some(duration)) =>
      tracer.buildSpan(name)
        .ignoreActiveSpan()
        .withStartTimestamp(timestamp.inMicroseconds)
        .start()
        .finish(timestamp.inMicroseconds + duration.inMicroseconds)
    case _ =>
  }

}
