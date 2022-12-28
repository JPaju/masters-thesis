package fi.jpaju.thesis.ziostuff

import zio.*

def zipPar[A, B](left: UIO[A], right: UIO[B]): UIO[(A, B)] =
  for
    fiber1 <- left.fork
    fiber2 <- right.fork
    a      <- fiber1.join
    b      <- fiber2.join
  yield (a, b)
