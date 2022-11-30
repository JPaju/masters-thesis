package fi.jpaju.thesis.ziostuff

import zio.*

val one: UIO[Int]        = ZIO.succeed(1)
val two: UIO[Int]        = one.map(_ + 1)
val discardOne: UIO[Int] = one.as(34) // same as map(_ => 34)

val _ = one.tap(n => ZIO.succeed(println(s"One: $n")))
val _ = one.debug("One") // Same as above, prints "One: "34"
