package fi.jpaju.thesis.casestudy

import zio.*
import zio.test.*

import java.time.*

object Testing extends ZIOSpecDefault:
  def spec = suite("Testing")(
    test("revoke should set current time as the revokation time") {
      val fixedTime: Instant = Instant.parse("2023-03-30T19:34:28Z")

      for
        apikeyService <- ZIO.service[ApikeyService]
        apikey        <- apikeyService.create("test apikey description")

        _ <- TestClock.setTime(fixedTime) // Set current time
        _ <- apikeyService.revoke(apikey) // Perform logic under test

        // This is verifiable using the provided in-memory repository
        allApikeys    <- FakeApikeyRepository.getAll
        revokedApikey <- ZIO.getOrFail(allApikeys.find(_ == apikey))

      // Assert that the fixed time was used as the revokation time
      yield assertTrue(revokedApikey.isRevokedAt(fixedTime))
    }.provide(
      ApikeyService.layer,
      FakeApikeyRepository.layer,
      FakeKeyGen.layer,
    ) // Test is configured with real ApikeyService and fake dependencies
  )

object FakeKeyGen:
  val layer: ZLayer[Any, Nothing, KeyGen] = ???

object FakeApikeyRepository:
  val layer: ZLayer[Any, Nothing, ApikeyRepository] = ???
  def getAll: UIO[List[Apikey]]                     = ???
