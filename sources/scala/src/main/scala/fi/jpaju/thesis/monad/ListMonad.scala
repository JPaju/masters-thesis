package fi.jpaju.thesis
package monad

object ListMonad:

  val numbers = List(1, 2, 3, 4, 5)
  val strings = List("one", "two", "three", "four", "five")

  val cartesianProduct =
    for
      n <- numbers
      s <- strings
    yield (n, s)
