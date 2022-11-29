package fi.jpaju.thesis
package scalalang

object DeclarationSiteVariance:
  class Invariant[A]      // Invariance is the default
  class Covariant[+A]     // Covariance denoted with +
  class Contravariant[-A] // Contravariance denoted with -

  class Supertype
  class Subtype extends Supertype

  object Proofs:
    type A

    // No inheritance hierarchy, type equality when precisely same types
    summon[Invariant[A] =:= Invariant[A]] // for all A

    // Inheritance hierarchy is same as with the type parameter
    summon[Covariant[Subtype] <:< Covariant[Supertype]]

    // Inheritance hierarchy is the reverse of the type parameter'
    summon[Contravariant[Supertype] <:< Contravariant[Subtype]]
