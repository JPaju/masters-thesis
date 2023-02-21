effect exception
  ctl raise (exc : e) : a

// e is the polymorphic effect variable
fun mapZ(lst : list<a>, f : a -> e b) : e list<b>
  match lst
    Cons(head, tail)  -> Cons(f(head), mapZ(tail, f))
    Nil               -> Nil

val nums: list<int> = [1, 2, 3, 4, 5]

val pure: list<int> = mapZ(nums, fn(n) n * 2)

fun effectful(): exception list<int>
  mapZ(nums, fn(n) if n > 5 then raise("Too large") else n * 2)

fun main()
  println("Testing 123")
