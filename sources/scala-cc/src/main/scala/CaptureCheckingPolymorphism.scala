import java.io.*
import scala.annotation.*

import language.experimental.fewerBraces


// Define effect polymorphic List.map function. Captured capabilities
// are those captured by the function 'f', annotated as '{f}'.
extension [A](as: List[A]) def map[B](f: A => B): {f} List[B] =
    as match
      case head :: tail => f(head) :: tail.map(f)  
      case Nil          => Nil

// Define a logger capability
@capability class Logger:
  def log(msg: String): Unit = println(msg)

// Define a function that uses the logger capability
def doSomething(ns: List[Int], logger: Logger) =

  // The map function is pure, thus capture set is empty
  val pureExpression: {} List[Int] = ns.map(n => n * 2)

  // Closes over logger, and that is reflected in the capture set
  val capturesLogger: {logger} List[Int] = ns.map { n =>
    logger.log("multiplying " + n)
    n * 2
  }

@main def captureCheckingPolymorphism =
  doSomething(List(1,2,3,4, 34), Logger())
