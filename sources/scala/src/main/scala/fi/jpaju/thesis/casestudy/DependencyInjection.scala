package fi.jpaju.thesis.casestudy

import zio.*

object Main:
  // Represents the whole program before its dependencies are provided
  val program: ZIO[ApikeyService, Nothing, Unit] = ???

  val run: ZIO[Any, Nothing, Unit] = program.provide(
    ApikeyService.layer,
    PostgresApikeyRepository.layer,
    KeyGen.layer,
    Database.dataSourceLayer,
  )
