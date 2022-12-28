package fi.jpaju.thesis.ziostuff

import zio.*

def log(msg: String): UIO[Unit] = ZIO.debug(msg)
def hangForever(tag: String): UIO[Nothing] =
  log(s"Start: $tag") *> ZIO.never.onInterrupt(log(s"Stop: $tag"))

val supervision: UIO[Unit] = for
  _     <- hangForever("fork").fork
  _     <- hangForever("forkDaemon").forkDaemon
  scope <- Scope.make
  _     <- hangForever("forkIn").forkIn(scope)
  _     <- ZIO.scoped(hangForever("forkScoped").forkScoped)
  _     <- scope.close(Exit.unit)
yield ()

// Start order(non-deterministic): fork, forkDaemon, forkIn, forkScoped
// Interruption order: forkScoped, forkIn, fork
// forkDaemon is not interrupted

object FiberSupervision extends ZIOAppDefault:
  val run = supervision
