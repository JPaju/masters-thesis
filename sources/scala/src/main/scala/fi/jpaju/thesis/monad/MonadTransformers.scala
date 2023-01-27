package fi.jpaju.thesis.monad

object SyntaxOverhead:
  def fn(str: String): IO[Either[Unit, Int]] = ???

  val ioEitherString: IO[Either[Unit, String]] = IO(Right("foo"))

  val ioEitherInt: IO[Either[Unit, Int]] =
    ioEitherString.flatMap(either =>
      either.fold(
        error => IO(Left(error)),
        success => fn(success),
      ),
    )

end SyntaxOverhead

object SubtleBugs:

  def mayFail: IO[Either[String, Unit]]  = ???
  def wontFail: IO[Either[Nothing, Int]] = ???

  // format: off
  val program: IO[Either[String, Int]] =
    for
                      // Type of _ is Either[String, Unit]
      _   <- mayFail  // Even if line evaluates to Left
      res <- wontFail // ... this line will still be executed
    yield res
  // format: on

end SubtleBugs

case class EitherT[F[_], E, A](effect: F[Either[E, A]]):
  def leftWiden[E1 >: E]: EitherT[F, E1, A] =
    this.asInstanceOf[EitherT[F, E1, A]]

object EitherT:
  given [E, F[_]: Monad]: Monad[[A] =>> EitherT[F, E, A]] with
    def pure[A](a: A): EitherT[F, E, A] =
      EitherT(Monad[F].pure(Right(a)))

    extension [A](self: EitherT[F, E, A])
      def flatMap[B](f: A => EitherT[F, E, B]): EitherT[F, E, B] =
        EitherT(
          self.effect.flatMap {
            case Left(e)  => Monad[F].pure(Left(e))
            case Right(a) => f(a).effect
          },
        )

  def mayFail: EitherT[IO, String, Unit]  = ???
  def wontFail: EitherT[IO, Nothing, Int] = ???

  // format: off
  val program: EitherT[IO, String, Int] =
    for
                        // Type of _ is Unit
      _     <- mayFail  // If this line evaluates to Left
      value <- wontFail // This line won't be executed
                .leftWiden
    yield value
  // format: on
