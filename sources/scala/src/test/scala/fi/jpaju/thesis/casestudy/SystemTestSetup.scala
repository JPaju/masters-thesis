package fi.jpaju.thesis.casestudy

import zio.*
import zio.test.*

case class EnvVars(values: Map[String, String])

object SystemTestSetup:
  // This layer starts the application in the background and
  // configures it by setting environment variables before starting.
  val layer: ZLayer[EnvVars, Nothing, Unit] =
    TestSystem.default >>> ZLayer.scoped {
      for
        envVars <- ZIO.service[EnvVars]
        _       <- setEnvironmentVariables(envVars)
        _       <- startApp
      yield ()
    }

  // The application keeps it running while tests are finished.
  // Delay makes sure the application has had time to start.
  def startApp = Main.run.forkScoped *> ZIO.sleep(2.second)

  def setEnvironmentVariables(envVars: EnvVars) =
    ZIO.foreachDiscard(envVars.values) { (key, value) =>
      TestSystem.putEnv(key, value)
    }
