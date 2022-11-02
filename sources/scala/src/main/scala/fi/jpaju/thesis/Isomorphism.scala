package fi.jpaju.thesis

object Isomorphisms:
  def optionToEither[A]: Option[A] => Either[Unit, A] = {
    case None    => Left(())
    case Some(a) => Right(a)
  }

  def eitherToOption[A]: Either[Unit, A] => Option[A] = {
    case Left(()) => None
    case Right(a) => Some(a)
  }

  def optionToEither2[A](option: Option[A]): Either[Unit, A] =
    option.fold(Left(()))(a => Right(a))

  def eitherToOption2[A](either: Either[Unit, A]): Option[A] =
    either.fold(unit => None, a => Some(a))

  case class Iso[A, B](to: A => B, from: B => A)

  def optionEitherIsomorphism[A]: Iso[Option[A], Either[Unit, A]] =
    Iso(
      to = {
        case None    => Left(())
        case Some(a) => Right(a)
      },
      from = {
        case Left(_)  => None
        case Right(a) => Some(a)
      },
    )
