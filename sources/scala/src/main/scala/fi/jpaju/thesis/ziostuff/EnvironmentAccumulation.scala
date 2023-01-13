package fi.jpaju.thesis.ziostuff
package environmentaccumulation

import zio.*

val num1: ZIO[String, Nothing, Int] = ???
val num2: ZIO[Int, Nothing, Int]    = ???
val num3: ZIO[Any, Nothing, Int]    = ???

// 'Any' does not appear in the environment type
val composed: ZIO[String & Int, Nothing, Int] =
  for
    n1 <- num1
    n2 <- num2
    n3 <- num3
  yield n1 + n2 + n3
