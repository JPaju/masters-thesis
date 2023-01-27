package fi.jpaju.thesis
package monad

trait Functor[F[_]]:
  extension [A](fa: F[A])
    def map[B](f: A => B): F[B]
    def widen[B >: A]: F[B] = fa.map(identity)

trait Monad[F[_]] extends Functor[F]:
  def pure[A](a: A): F[A]
  extension [A](fa: F[A])
    def flatMap[B](f: A => F[B]): F[B]
    def map[B](f: A => B): F[B] =
      flatMap(a => pure(f(a)))

object Monad:
  def apply[F[_]: Monad] = summon[Monad[F]]
