effect exception
  ctl raise (exc : e) : a

fun to-maybe(might-throw : () -> <exception|x> a) : x maybe<a>
  with handler
    raise(e)  -> Nothing
    return(a) -> Just(a)
  might-throw()

fun divide(dividend : int, divisor : int)
  if divisor == 0 then raise("Division by zero")
  else dividend / divisor

fun main ()
  fun helper(a, b)
    val result = to-maybe { divide(a, b) }
    val result-list = list(result)
    println(show(result-list))

  helper(2, 1)
  helper(1, 0)
