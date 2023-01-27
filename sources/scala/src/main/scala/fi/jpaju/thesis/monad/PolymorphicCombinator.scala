package fi.jpaju.thesis
package monad

extension [A](as: List[A])
  def mapM[F[_]: Monad, B](f: A => F[B]): F[List[B]] =
    as match
      case Nil => Monad[F].pure(List.empty)
      case head :: tail =>
        for
          b  <- f(head)
          bs <- tail.mapM(f)
        yield b :: bs
