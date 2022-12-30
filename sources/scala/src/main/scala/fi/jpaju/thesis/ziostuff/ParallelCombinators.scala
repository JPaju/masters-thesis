package fi.jpaju.thesis.ziostuff
package parallercombinators

import zio.*

// Represents long-running interaction such as network or file system
def work(duration: Duration) = ZIO.sleep(duration)

val fast: IO[String, Int]  = work(50.millis) *> ZIO.fail("oops")
val slow: IO[Nothing, Int] = work(3.seconds) *> ZIO.succeed(34)

// 'slow' is interrupted after 50ms when 'fast' fails
val successInterrupted: IO[String, (Int, Int)] =
  fast.zipPar(slow)

// 'slow' is not interrupted because 'fast' is made infallible
val successNotInterrupted: IO[Nothing, (Either[String, Int], Int)] =
  fast.either.zipPar(slow)

object ParallelCombinators extends ZIOAppDefault:
  val run = successInterrupted.cause.debug("Cause")
