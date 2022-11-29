package fi.jpaju.thesis
package scalalang

trait A
trait B
trait C

def union =
  summon[(A | B) =:= (B | A)] // Commutative
  summon[A =:= (A | A)]       // Idempotent
  summon[A =:= (A | Nothing)] // Identity
  // summon[((A | B) | C) =:= (A | (B | C))] // Associative

def intersection =
  summon[(A & B) =:= (B & A)] // Commutative
  summon[A =:= (A & A)]       // Idempotent
  summon[A =:= (A & Any)]     // Identity
  // summon[((A & B) & C) =:= (A & (B & C))] // Associative
