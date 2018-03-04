package io.opentracing.contrib.finagle

import com.twitter.finagle.tracing._
import com.twitter.util.{Duration, Time}
import io.opentracing.mock.MockTracer
import org.scalatest._

class OpenTracingTracerSpec extends WordSpec with Matchers with LoneElement {

  "record" should {

    "set span timestamp and duration" in new TestContext {
      finagleTracer.record(fakeRecord.copy(
        timestamp = Time.fromMicroseconds(1000),
        duration = Some(Duration.fromMicroseconds(234))
      ))

      val span = openTracer.finishedSpans().loneElement
      span.startMicros() shouldBe 1000
      span.finishMicros() shouldBe 1234
    }

    "not create a span if duration is missing" in new TestContext {
      finagleTracer.record(fakeRecord.copy(
        timestamp = Time.fromMicroseconds(1000),
        duration = None
      ))

      openTracer.finishedSpans() shouldBe empty
    }

    "ignore active span" in new TestContext {
      openTracer.buildSpan("active").startActive(true)

      finagleTracer.record(fakeRecord)

      val span = openTracer.finishedSpans().loneElement
      span.references shouldBe empty
    }

    "set span name" in new TestContext {
      finagleTracer.record(fakeRecord.copy(
        annotation = Annotation.Rpc("name")
      ))

      val span = openTracer.finishedSpans().loneElement
      span.operationName shouldBe "name"
    }

  }

  private trait TestContext {
    lazy val openTracer = new MockTracer
    lazy val finagleTracer = new OpenTracingTracer(openTracer)

    lazy val fakeRecord = Record(
      TraceId(None, None, SpanId(2), None, Flags(0), None),
      Time.fromMicroseconds(0),
      Annotation.Rpc(""),
      Some(Duration.fromMicroseconds(1))
    )
  }
}
