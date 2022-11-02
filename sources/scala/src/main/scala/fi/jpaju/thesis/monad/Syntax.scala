package fi.jpaju.thesis.monad

object DirectVsMonadic:
  val num1: Int = 3
  val num2: Int = 4
  val sum: Int  = num1 + num2

  // format: off
  val optionNum1: Option[Int] = Option(3)
  val optionNum2: Option[Int] = Option(4)
  val optionSum: Option[Int]  =
    optionNum1.flatMap(n1 =>
      optionNum2.map(n2 =>
        n1 + n2))

object MonadicVsFor:
  import DirectVsMonadic.*

  val optionSum: Option[Int] =
    optionNum1.flatMap(n1 =>
      optionNum2.map(n2 =>
        n1 + n2))

  val optionSumFor: Option[Int] =
    for
      n1 <- optionNum1
      n2 <- optionNum2
    yield n1 + n2
