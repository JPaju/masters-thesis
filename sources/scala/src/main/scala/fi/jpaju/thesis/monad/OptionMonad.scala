package fi.jpaju.thesis
package monad

object OptionMonad:
  val maybeA: Option[Int] = Some(1)
  val maybeB: Option[Int] = Some(2)

  val maybeC = for
    a <- maybeA
    b <- maybeB
  yield a + b

  val desugaredC = maybeA.flatMap(a => maybeB.map(b => a + b))
