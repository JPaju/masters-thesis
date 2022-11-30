package fi.jpaju.thesis.ziostuff

import zio.*

// Cause in ZIO also includes traces omitted here
enum Cause[+E]:
  case Empty
  case Both(left: Cause[E], right: Cause[E])
  case Fail(value: E)
  case Die(value: Throwable)
  case Then(left: Cause[E], right: Cause[E])
  case Interrupt(fiberId: FiberId)

val a = ZIO.dieMessage("A")
val b = ZIO.fail("B").ensuring(ZIO.sleep(5.millis).timeout(1.milli))

val app = a.zipPar(b).cause.debug

val cause: Cause[String] =
  Cause.Both(
    Cause.Die(java.lang.RuntimeException("A")),
    Cause.Then(
      Cause.Fail("B"),
      Cause.Interrupt(FiberId.Runtime(6, 1234, Trace.empty)),
    ),
  )
