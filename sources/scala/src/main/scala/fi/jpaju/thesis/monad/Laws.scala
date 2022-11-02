package fi.jpaju.thesis.monad

object Associativity:
  def pure[A](a: A): Option[A] = Monad[Option].pure(a)

  val num1: Option[Int] = Some(1)
  val num2: Option[Int] = Some(2)
  val num3: Option[Int] = Some(3)

  val mustBeTrue = sumAll1 == sumAll2

  // format: off

  def sumAll1: Option[Int] = 
    num1.flatMap(n1 =>
      num2.flatMap(n2 =>
        num3.flatMap(n3 =>
          pure(n1 + n2 + n3))
      )
    )

  def sumAll2: Option[Int] =
    num1.flatMap(n1 =>
      num2.flatMap(n2 =>
        pure(n1 + n2))
    ).flatMap(sum12 =>
      num3.flatMap(n3 =>
        pure(sum12 + n3))
    )


  // format: on
end Associativity

object Identity:
  def pure[A](a: A): Option[A] = Monad[Option].pure(a)
  def f(n: Int)                = pure(n + 1)

  // Left identity
  val x: Int = 1
  pure(x).flatMap(n => f(n)) == f(x)

  // Right identity
  val num: Option[Int] = pure(1)
  num.flatMap(n => pure(n)) == num
