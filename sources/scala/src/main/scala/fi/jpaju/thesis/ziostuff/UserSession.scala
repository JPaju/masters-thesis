package fi.jpaju.thesis.ziostuff
package usersession

import zio.*

final case class TokenError()
final case class UserSession()
final case class Result()

def getToken: UIO[String] = ???

// ==========================================================================================
// ==========================================================================================

trait UserService:
  def validate(token: String): IO[TokenError, UserSession]

object UserService:
  def withSessionFromToken[R: Tag, E, A](token: String)(
      needsSession: ZIO[R & UserSession, E, A]
  ): ZIO[R & UserService, E | TokenError, A] =
    val session = ZIO.serviceWithZIO[UserService](_.validate(token))
    val layer   = ZLayer(session) // Create a ZLayer from ZIO value
    layer(needsSession) // Provide session as layer to ZIO workflow

val businessLogic: ZIO[UserSession, Nothing, Result] = ???

val program: ZIO[UserService, TokenError, Result] = for
  token  <- getToken // For example from a HTTP request
  result <- UserService.withSessionFromToken(token) { businessLogic }
yield result
