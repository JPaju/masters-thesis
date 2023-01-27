package fi.jpaju.thesis
package monad

type Id[A] = A

given Monad[Id] with
  def pure[A](a: A): Id[A] = a
  extension [A](a: Id[A])
    def flatMap[B](f: A => Id[B]): Id[B] =
      f(a)

val sumId = for
  a <- 1
  b <- 2
yield a + b
