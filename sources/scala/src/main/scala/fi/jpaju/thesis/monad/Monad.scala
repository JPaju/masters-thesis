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
  given Monad[Option] = ???

  def apply[F[_]: Monad] = summon[Monad[F]]

object ForSyntax:
  trait Monad[F[_]]:
    def pure[A](a: A): F[A]
    extension [A](fa: F[A])
      def flatMap[B](f: A => F[B]): F[B]
      def map[B](f: A => B): F[B] = // for-syntax requires map
        flatMap(a => pure(f(a)))

  type Id[A] = A
  given Monad[Id] = ??? // Implementation given earlier

  val three: Id[Int] = for
    a <- 1
    b <- 2
  yield a + b
