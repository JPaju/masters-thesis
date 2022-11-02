package fi.jpaju.thesis

object Show:

  // Define type class
  trait Show[A]:
    extension (a: A) def show: String

  case class Person(age: Int, name: String)

  // Define type class instance
  given Show[Person] with
    extension (a: Person) def show: String = s"${a.name} is ${a.age} years old"

  // Use the type class
  def showAll[A: Show](as: List[A]): List[String] =
    as.map(a => a.show)

end Show

object Ordering:

  case class Money(amount: Int)

  trait Ordering[A]:
    extension (lhs: A) def <(rhs: A): Boolean

  def sort[A: Ordering](as: List[A]): List[A] = as.sortWith(_ < _)

  given Ordering[Money] with
    extension (self: Money) def <(that: Money): Boolean = self.amount < that.amount

  val sorted = sort(List(Money(3), Money(1), Money(2)))

end Ordering

import Ordering.*
import Show.*

@main def orderingMain: Unit =
  println(sorted)

@main def showMain: Unit =
  val persons = List(Person(20, "John"), Person(30, "Mary"), Person(40, "Bob"), Person(50, "Alice"))
  println(showAll(persons))
