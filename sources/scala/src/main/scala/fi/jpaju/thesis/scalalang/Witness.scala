package fi.jpaju.thesis
package scalalang

enum Maybe[+A]:
  case Just(a: A)
  case Nothing

  def flatten[B](using evidence: A <:< Maybe[B]): Maybe[B] =
    this match
      case Just(a) => a
      case Nothing => Nothing

val compiles = Maybe.Just(Maybe.Just(1)).flatten
// val doesNotCompile = Maybe.Just(1).flatten // Cannot prove that Int <:< Maybe[B]
