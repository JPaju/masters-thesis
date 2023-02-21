package fi.jpaju.thesis

extension [A](as: List[A])
  def map[B](f: A => B): List[B] =
    as match
      case head :: tail => f(head) :: tail.map(f)
      case Nil          => Nil

// The type signatures of all expressions are identical
// Even though the last one may throw an exception
val nums: List[Int] = List(1, 2, 3, 4, 5)
val pure: List[Int] = nums.map(n => n * 2)
val effectful: List[Int] = nums.map { n =>
  if n > 5 then throw RuntimeException("Too large") else n * 2
}

@main
def unrestrictedSideEffects =
  println(s"Pure: $pure")
  println(s"Effectful: $effectful")
