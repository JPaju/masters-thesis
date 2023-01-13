package fi.jpaju.thesis.ziostuff
package zlayers

import zio.*

trait A
trait B
trait C
trait D


// format: off
val layerA: ZLayer[Any,   Nothing, A] = ???
val layerB: ZLayer[A,     Nothing, B] = ???
val layerC: ZLayer[A,     Nothing, C] = ???
val layerD: ZLayer[B & C, Nothing, D] = ???
// format: on
