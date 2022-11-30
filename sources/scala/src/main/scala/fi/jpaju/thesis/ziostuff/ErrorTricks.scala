package fi.jpaju.thesis.ziostuff

import zio.IsSubtypeOfOutput

trait ZIO[-R, +E, +A]:
  def flip: ZIO[R, A, E]

  def either: ZIO[R, Nothing, Either[E, A]]
  def absolve[E1 >: E, B](using A <:< Either[E1, B]): ZIO[R, E1, B]

  def cause: ZIO[R, Nothing, Cause[E]]
  def uncause[E1 >: E](using A <:< Cause[E1]): ZIO[R, E1, Unit]
