package fi.jpaju.thesis

trait Ordering[A]:
  extension (lhs: A) def <(rhs: A): Boolean

case class Money(amount: Int)

given Ordering[Money] with
  extension (self: Money) def <(that: Money): Boolean =
    self.amount < that.amount

// Compatible with any data type that has instance for Ordering
def sort[A: Ordering](as: List[A]): List[A] = as.sortWith(_ < _)

val sorted = sort(List(Money(3), Money(1), Money(2)))


@main def orderingMain: Unit =
  println(sorted)
