package fi.jpaju.thesis.casestudy

import zio.*

import javax.sql.DataSource
import java.time.Instant

case class InvalidDescription(description: String)
case class DuplicateApikey(apikey: Apikey)
case class ApikeyNotFound(apikey: Apikey)
case class Apikey(description: String, secret: String):
  def isRevokedAt(time: Instant): Boolean = ???

trait Query[A]

trait KeyGen:
  def generateKey: UIO[String]

trait ApikeyRepository:
  def add(apikey: Apikey): IO[DuplicateApikey, Apikey]

object KeyGen:
  val layer: ZLayer[Any, Nothing, KeyGen] = ???

object Database:
  val dataSourceLayer: ZLayer[Any, Nothing, DataSource] = ???
