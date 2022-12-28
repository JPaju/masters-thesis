package fi.jpaju.thesis.ziostuff

import zio.*

val work = ZIO.sleep(1.second) *> ZIO.debug("Work completed")
val parentZIO = for
  childFiber <- work.fork
  _          <- ZIO.debug("Parent forked child fiber")
  _          <- childFiber.join
  _          <- ZIO.debug("Parent joined child fiber")
yield ()

// When executed prints:
// Parent forked child fiber
// Work completed
// Parent joined child fiber

object ForkingMain extends ZIOAppDefault:
  val run = parentZIO
