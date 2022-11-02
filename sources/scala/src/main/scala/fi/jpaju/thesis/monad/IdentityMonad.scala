package fi.jpaju.thesis
package monad

class IdentityMonad:
  type Id[A] = A

  given Monad[Id] = ???

  val sumId = for
    a <- 1
    b <- 2
  yield a + b

end IdentityMonad
