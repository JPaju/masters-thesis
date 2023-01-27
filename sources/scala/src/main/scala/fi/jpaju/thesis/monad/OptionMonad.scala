package fi.jpaju.thesis
package monad

given Monad[Option] with
  def pure[A](a: A): Option[A] = Some(a)
  extension [A](self: Option[A])
    def flatMap[B](f: A => Option[B]): Option[B] =
      self.flatMap(f)

object OptionMonad:
  val maybeA: Option[Int] = Some(1)
  val maybeB: Option[Int] = Some(2)

  val maybeC = for
    a <- maybeA
    b <- maybeB
  yield a + b

  val desugaredC = maybeA.flatMap(a => maybeB.map(b => a + b))
