package fi.jpaju.thesis.ziostuff
package scopes

import zio.*

def log(msg: String): UIO[Unit] = ZIO.debug(msg)

val intResource: ZIO[Scope, Nothing, Int] = ZIO.acquireRelease(
  acquire = log("acquire int").as(34),
)(release = int => log(s"release $int"))

val stringResource: ZIO[Scope, Nothing, String] = ZIO.acquireRelease(
  acquire = log("acquire string").as("foo"),
)(release = str => log(s"release $str"))

// "acquire int", "acquire string", "release foo", "release 34"
val program1 = ZIO.scoped { intResource *> stringResource }

// "acquire int", "release 34", "acquire string", "release foo"
val program2 = ZIO.scoped(intResource) *> ZIO.scoped(stringResource)

object ScopeMain extends ZIOAppDefault:
  val run = program1
