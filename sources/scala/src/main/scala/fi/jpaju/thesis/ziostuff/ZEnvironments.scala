package fi.jpaju.thesis.ziostuff

import zio.*

// Can be thought of as: Map(Int -> 42, String -> "foo")
val environment: ZEnvironment[String & Int] =
  ZEnvironment
    .empty
    .add[Int](42)       // Explicit types here are not required
    .add[String]("foo") // but they are added for clarity

// Values from the environment can be accessed by their type
val int    = environment.get[Int]    // 42
val string = environment.get[String] // "foo"
