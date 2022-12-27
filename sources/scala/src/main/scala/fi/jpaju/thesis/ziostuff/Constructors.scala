package fi.jpaju.thesis.ziostuff
package constructors

import zio.*

import java.io.*

val pureValue: UIO[Int]   = ZIO.succeed(1)
val sideEffect: UIO[Unit] = ZIO.succeed(println("Hello World!"))

val either: Either[String, Int] = ???
val fromEither: IO[String, Int] = ZIO.fromEither(either)

val effect: IO[Throwable, Array[Byte]] = ZIO.attempt {
  val file = new File("numbers.txt")
  val is   = new FileInputStream(file) // can throw IOException
  is.readAllBytes() //  can throw IOException
}

val error: IO[String, Nothing] = ZIO.fail("Error")
val defect: UIO[Nothing]       = ZIO.die(new Exception("Error"))
