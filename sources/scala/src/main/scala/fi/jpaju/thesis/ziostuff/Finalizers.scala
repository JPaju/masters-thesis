package fi.jpaju.thesis.ziostuff

import zio.*

val finalizer = ZIO.succeed(println("Finalizer executed"))

val success: UIO[Int]    = ZIO.succeed(1).ensuring(finalizer)
val error: IO[Int, Int]  = ZIO.fail(42).ensuring(finalizer)
val defect: UIO[Nothing] = ZIO.dieMessage("No").ensuring(finalizer)

val interruption: UIO[Unit] = for
  fiber <- ZIO.sleep(1.second).ensuring(finalizer).fork
  _     <- fiber.interrupt
yield ()
