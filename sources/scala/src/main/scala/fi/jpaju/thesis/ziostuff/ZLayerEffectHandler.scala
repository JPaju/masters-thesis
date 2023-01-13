package fi.jpaju.thesis.ziostuff
package zlayereffecthandler

import zio.*

import zlayers.*

trait ZLayer[-RIn, +E, +ROut]:
  // Environmental requirement of type ROut is removed, and RIn is
  // added to the ZIO received as parameter. Real implementation also
  // changes error type, but it is skipped here for brevity
  def apply[R, E1, A](zio: ZIO[ROut & R, E1, A]): ZIO[RIn & R, E1, A]

val handleAtoB: ZLayer[B, Nothing, A] = ??? // Changes A -> B
val handleB: ZLayer[Any, Nothing, B]  = ??? // Eliminates B

val effect: ZIO[A & Boolean, Nothing, Int] = ???

// ZLayer is polymorphic in the type of environmental requirement
// Here it removes B, adds A, and leaves Boolean as is
val handledA: ZIO[B & Boolean, Nothing, Int] = handleAtoB(effect)

// ZLayer (handler) for B does not have any requirements, so B is
// removed from the environment entirely, leaving only Boolean
val handledB: ZIO[Boolean, Nothing, Int] = handleB(handledA)
