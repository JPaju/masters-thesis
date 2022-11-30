package fi.jpaju.thesis.ziostuff

import zio.*

type Error = ErrorA | ErrorB | ErrorC

val mayFail: IO[Error, Int] = ???

val allHandled: IO[Nothing, Int] =
  mayFail.catchAll(err => ZIO.succeed(0))

val someHandled: IO[Error, Int] =
  mayFail.catchSome { case _: ErrorA => ZIO.succeed(34) }

val folded: UIO[Int] = mayFail.fold(e => -1, n => n + 10)

val withFallback: IO[Nothing, Int] = mayFail.orElse(ZIO.succeed(42))
