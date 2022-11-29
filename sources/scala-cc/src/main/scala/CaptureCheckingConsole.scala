import scala.annotation.*

// Class is marked as capability by annotating it
@capability class Console:
  def printLine(x: Any): Unit = println(x)

// Required capabilites are declaired as constructor parameters
class Example(using val console: Console):
  val numbers: List[Int] = List(1, 2, 3)

  // When function passed to map does not capture capabilities
  // the resulting value does not capture anything
  val doubled: List[Int] = numbers.map(n => n * 2)

  // Here the function passed to map captures console capability
  // thus the resulting value captures same capability
  // indicated in the type as '{console}'
  val printed: {console} List[Int] = numbers.map { n =>
    console.printLine(n)
    n * 2
  }
