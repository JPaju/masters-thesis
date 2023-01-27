package fi.jpaju.thesis
package monad

case class IO[+A](run: () => A)

given Monad[IO] with
  def pure[A](a: A): IO[A] = IO(() => a)
  extension [A](io: IO[A])
    def flatMap[B](f: A => IO[B]): IO[B] =
      IO(() => f(io.run()).run())

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

@main def IOmain =

  val program =
    for
      _    <- IO.printLine("What's your name?")
      name <- IO.readLine
      _    <- IO.printLine(s"Hello, $name")
    yield ()

  IO.unsafeRun(program)
