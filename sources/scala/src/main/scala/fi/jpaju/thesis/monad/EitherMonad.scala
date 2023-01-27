package fi.jpaju.thesis
package monad

extension [E, A](either: Either[E, A])
  def <>[E1, A1](fallback: Either[E1, A1]): Either[E1, A | A1] =
    either.fold(_ => fallback, a => Right(a))

  def getRight(using ev: E =:= Nothing): A =
    either.fold(e => ???, identity)

object EitherMonad:
  import Either as SEither
  import Right as SRight
  import Left as SLeft

  import Either.*

  enum Either[+E, +A]:
    case Left(e: E)
    case Right(a: A)

  given [E]: Monad[[A] =>> Either[E, A]] with
    def pure[A](a: A): Either[E, A] = Right(a)
    extension [A](either: Either[E, A])
      def flatMap[B](f: A => Either[E, B]): Either[E, B] =
        either match
          case Left(e)  => Left(e)
          case Right(a) => f(a)

  val success        = Right(1)
  val anotherSuccess = Right(2)
  val failure        = Left("error")

  val sum = for
    a <- success
    _ <- failure
    b <- anotherSuccess
  yield a + b
