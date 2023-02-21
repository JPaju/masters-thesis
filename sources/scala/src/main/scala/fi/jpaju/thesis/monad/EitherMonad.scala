package fi.jpaju.thesis
package monad

import scala.{ Either as SEither, Left as SLeft, Right as SRight }

extension [E, A](either: SEither[E, A])
  def <>[E1, A1](fallback: SEither[E1, A1]): SEither[E1, A | A1] =
    either.fold(_ => fallback, a => SRight(a))

  def getRight(using ev: E =:= Nothing): A =
    either.fold(e => ???, identity)

given [E]: Monad[[A] =>> SEither[E, A]] with
  def pure[A](a: A): SEither[E, A] = SRight(a)
  extension [A](either: SEither[E, A])
    def flatMap[B](f: A => SEither[E, B]): SEither[E, B] =
      either match
        case SLeft(e)  => SLeft(e)
        case SRight(a) => f(a)

object EitherMonad:
  import fi.jpaju.thesis.monad.EitherMonad.Either.*

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
