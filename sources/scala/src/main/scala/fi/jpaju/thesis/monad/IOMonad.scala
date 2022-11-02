package fi.jpaju.thesis
package monad

import java.util.concurrent.CountDownLatch

case class IO[+A](run: () => A)

object IO:
  def apply[A](a: A): IO[A] =
    IO(() => a)

  def unsafeRun[A](io: IO[A]): A =
    io.run()

  def printLine(x: Any): IO[Unit] =
    IO(() => println(x))

  val readLine: IO[String] =
    IO(() => scala.io.StdIn.readLine())

  def sleep(millis: Long): IO[Unit] =
    IO(() => Thread.sleep(millis))

  extension [A](self: IO[A])
    def repeat(n: Int): IO[A] =
      if n <= 0 then self
      else self.flatMap(x => self.repeat(n - 1))

    def *>[B](other: IO[B]): IO[B] =
      self.flatMap(_ => other)

    def forever: IO[Nothing] =
      self.flatMap(_ => self.forever)
  end extension
end IO

given Monad[IO] = new:
  def pure[A](a: A): IO[A] = IO(a)
  extension [A](io: IO[A])
    def flatMap[B](f: A => IO[B]): IO[B] =
      IO(() => f(io.run()).run())

end given

type IO2[A] = () => A

given Monad[IO2] = new:
  def pure[A](a: A): IO2[A] = () => a
  extension [A](io: IO2[A])
    def flatMap[B](f: A => IO2[B]): IO2[B] =
      () => f(io())()

end given

object IO2:
  def delay[A](a: => A): IO2[A] =
    () => a

  def printLine(x: Any): IO2[Unit] =
    delay(println(x))

  def readLine: IO2[String] =
    delay(scala.io.StdIn.readLine())

  def sleep(millis: Long): IO2[Unit] =
    delay(Thread.sleep(millis))

  def async[A](register: (A => Any) => Any): IO2[A] =
    delay {
      val latch     = new CountDownLatch(1)
      var result: A = null.asInstanceOf[A]
      register { a =>
        result = a
        latch.countDown()
      }

      latch.await()
      result
    }

  def unsafeRun[A](io: IO2[A]): A =
    io()

  extension [A](self: IO2[A])
    def repeat(n: Int): IO2[A] =
      if n <= 0 then self
      else self.flatMap(x => self.repeat(n - 1))

    def *>[B](other: IO2[B]): IO2[B] =
      self.flatMap(_ => other)

    def forever: IO2[Nothing] =
      self.flatMap(_ => self.forever)
  end extension

end IO2

@main def IOmain =
  import IO2.*

  val program1 =
    for
      _    <- IO.printLine("What's your name?")
      name <- IO.readLine
      _    <- IO.printLine(s"Hello, $name")
    yield ()

  val program2 =
    for
      _    <- IO2.printLine("What's your name?")
      name <- IO2.readLine
      _    <- IO2.printLine(s"Hello, $name")
    yield ()

  val callbacked = IO2.async[Int] { cb =>
    println("Before sleep")
    Thread.sleep(2000)
    println("After sleep")

    cb(1)
  }

  val asyncProgram =
    for
      _ <- IO2.printLine("Before callbacked")
      n <- callbacked
      _ <- IO2.printLine(s"Callback result is $n")
    yield ()

  // IO1.unsafeRun(program1)
  IO2.unsafeRun(program2)
