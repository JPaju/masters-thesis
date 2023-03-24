package fi.jpaju.thesis.ziostuff
package state

import zio.*

val statefulComputation: URIO[ZState[Int], Int] = for
  state <- ZIO.getState[Int]       // Access state
  _     <- ZIO.setState(state + 1) // Modify state
yield state

val statefulProgram: URIO[ZState[Int], Unit] = for
  _ <- statefulComputation.debug("First")
  _ <- statefulComputation.debug("Second")
  _ <- ZIO.getState[Int].debug("Last")
yield ()

// Provide initial state (0) to the stateful computation
// When executed prints: "First: 0", "Second: 1", "Last: 2"
val stateProvided: UIO[Unit] = ZIO.stateful(0)(statefulProgram)

object StateMain extends ZIOAppDefault:
  val run = stateProvided
