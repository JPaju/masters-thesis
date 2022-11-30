package fi.jpaju.thesis.ziostuff
package binarycombinators

import zio.*

val num  = ZIO.succeed(34)
val str  = ZIO.succeed("A string value")
val tell = ZIO.succeed(println("Hello World"))

// All three below are semantically equal
val v1: UIO[(Int, String)] = num.flatMap(n => str.map(s => (n, s)))
val v2: UIO[(Int, String)] = num.zipWith(str)((n, s) => (n, s))
val v3: UIO[(Int, String)] = num.zip(str)

val zipRight: UIO[Int] = tell.zipRight(num)
val zipLeft: UIO[Int]  = num.zipLeft(tell)

// Evaluation order: tell, num, tell. Return value: num
val toldTwoTimes: UIO[Int] = tell *> num <* tell
