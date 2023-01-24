package fi.jpaju.thesis
package scalalang

import java.io.*

object Basics:
  trait Foo // Define an interface
  class Bar extends Foo // Define a class inheriting from Foo

  // Define variables/constants
  var mutableFoo: Foo = Bar() // Explicit type is Foo
  val immutableBar    = Bar() // Inferred type is Bar
  lazy val lazyPlus   = 1 + 1 // Computed lazily and cached

  // Type argument here is Int
  val genericType: List[Int] = List(1, 2, 3)

  // Type parameters are declared between '[' and ']'
  def genericMethod[A](a: A): A = a

  // Type parameter constraints:
  // 'A' must be supertype of 'Bar' and  'B' must be subtype of 'Foo'
  def typeBounds[A >: Bar, B <: Foo](a: A): B = ???

  // ??? is defined in the standard library. it can replace any
  // expression; it's type is Nothing, the bottom type
  def `???` : Nothing = throw new NotImplementedError

  // => specifies 'by-name' calling convention:
  // The parameter is evaluated every time it is used (2 times here)
  def byNameParameter(n: => Int) = n + n
