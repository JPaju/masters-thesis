package fi.jpaju.thesis.ziostuff
package environmentsimple

import zio.*

type Result = Nothing

def makeRequest(url: String): ZIO[Any, Nothing, Result] = ???

// ==========================================================================================
// ==========================================================================================

case class Configuration(url: String)

val useConfiguration: ZIO[Configuration, Nothing, Result] =
  ZIO.serviceWithZIO[Configuration](conf => makeRequest(conf.url))

val configurationLayer: ZLayer[Any, Nothing, Configuration] =
  ZLayer.succeed(Configuration(url = "https://example.com"))

val configurationProvided: ZIO[Any, Nothing, Result] =
  configurationLayer(useConfiguration)
