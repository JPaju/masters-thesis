package fi.jpaju.thesis
package scalalang

val someEvensMultipliedByTen: PartialFunction[Option[Int], Int] = {
  case Some(n) if n % 2 == 0 => n * 10
}

val opts  = List(None, Some(2), None, Some(3), Some(4))
val somes = opts.collect(someEvensMultipliedByTen) // List(20, 40)

@main
def partialFunctionMain =
  println(somes)
