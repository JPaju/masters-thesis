package fi.jpaju.thesis.ziostuff
package state

import zio.*

val statefulComputation: URIO[ZState[Int], Int] = for
  state <- ZIO.getState[Int]       // Access state
  _     <- ZIO.setState(state + 1) // Modify state
yield state

val statefulProgram: URIO[ZState[Int], Unit] = for
  _ <- statefulComputation.debug("First state")
  _ <- statefulComputation.debug("Second state")
  _ <- ZIO.getState[Int].debug("Final state")
yield ()

// Provide initial state (0) to the stateful computation
// When executed prints:
// "First state: 0", "Second state: 1", "Final state: 2"
val stateRequirementProvided: URIO[Any, Unit] =
  ZIO.stateful(0)(statefulProgram)

object StateMain extends ZIOAppDefault:
  val run = stateRequirementProvided
