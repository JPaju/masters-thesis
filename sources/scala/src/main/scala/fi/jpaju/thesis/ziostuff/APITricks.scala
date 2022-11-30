package fi.jpaju.thesis.ziostuff
package apitricks

import zio.*

trait ZIO[R, E, A]:
  // A Zippable type class is synthesized for every 'A' and 'B'
  // The dependent type 'Out' flattens tuples and filters units
  def zip[B](
      that: ZIO[R, E, B]
    )(using zippable: Zippable[A, B]
    ): ZIO[R, E, zippable.Out]

  // orElse provides a way to define a fallback computation in
  // case the current computation fails. Instance of CanFail
  // type class is synthesized for every 'E', except for 'Nothing'
  def orElse[R, A1](
      that: ZIO[R, E, A1]
    )(using ev: CanFail[E]
    ): ZIO[R, E, A | A1]

val num: UIO[Int]   = ZIO.succeed(1)
val unit: UIO[Unit] = ZIO.succeed(())

// Type without Zipped would be UIO[(((Int, Int), Unit), Int)]
val zipped: UIO[(Int, Int, Int)] = num.zip(num).zip(unit).zip(num)

// Won't compile since 'one' cannot fail (error type is 'Nothing')
// val withFallback = num.orElse(unit)
