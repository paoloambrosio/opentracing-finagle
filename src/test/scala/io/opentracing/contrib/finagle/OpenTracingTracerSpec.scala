package io.opentracing.contrib.finagle

import com.twitter.finagle.tracing.Annotation.BinaryAnnotation
import com.twitter.finagle.tracing._
import com.twitter.util.{Duration, Time}
import io.opentracing.mock.MockTracer
import org.scalatest._

class OpenTracingTracerSpec extends WordSpec with Matchers with LoneElement {

  lazy val StopAnnotation = Annotation.ServerSend()

  "record" should {

    "publish and forget spans when receiving ClientRecv() or ServerSend()" in new TestContext {
      Seq(
        Annotation.ClientRecv(),
        Annotation.ServerSend()
      ).foreach { ann =>
        finagleTracer.record(fakeRecord.copy(annotation = ann))
      }

      openTracer.finishedSpans().size() shouldBe 2
    }

    "set span name when receiving Rpc(name)" in new TestContext {
      Seq(
        Annotation.Rpc("name"),
        StopAnnotation
      ).foreach { ann =>
        finagleTracer.record(fakeRecord.copy(annotation = ann))
      }

      val span = openTracer.finishedSpans().loneElement
      span.operationName() shouldBe "name"
    }

    "ignore active span" in new TestContext {
      openTracer.buildSpan("active").startActive(true)
      Seq(
        StopAnnotation
      ).foreach { ann =>
        finagleTracer.record(fakeRecord.copy(annotation = ann))
      }

      val span = openTracer.finishedSpans().loneElement
      span.references shouldBe empty
    }

    "record duration from first to last record" in new TestContext {
      Seq(
        fakeRecord.copy(timestamp = Time.fromMicroseconds(1000)),
        fakeRecord.copy(timestamp = Time.fromMicroseconds(1111)),
        fakeRecord.copy(timestamp = Time.fromMicroseconds(1234), annotation = StopAnnotation)
      ).foreach { finagleTracer.record }

      val span = openTracer.finishedSpans().loneElement
      span.startMicros() shouldBe 1000
      span.finishMicros() shouldBe 1234
    }

  }

  private trait TestContext {
    lazy val openTracer = new MockTracer
    lazy val finagleTracer = new OpenTracingTracer(openTracer)

    lazy val fakeRecord = Record(
      TraceId(None, None, SpanId(2), None, Flags(0), None),
      Time.fromMicroseconds(0),
      Annotation.BinaryAnnotation("",""),
      None
    )
  }
}
