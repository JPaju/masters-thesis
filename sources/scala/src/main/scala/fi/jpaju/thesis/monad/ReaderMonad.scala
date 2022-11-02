package fi.jpaju.thesis
package monad

object ReaderRecord:
  case class Reader[-R, +A](run: R => A)

  object Reader:
    def ask[R]: Reader[R, R] = Reader(r => r)

  given [R]: Monad[[A] =>> Reader[R, A]] = new:
    def pure[A](a: A): Reader[R, A] = Reader(_ => a)
    extension [A](self: Reader[R, A])
      def flatMap[B](f: A => Reader[R, B]): Reader[R, B] =
        Reader(r => f(self.run(r)).run(r))

end ReaderRecord

object ReaderAlias:
  type Reader[-R, +A] = R => A

  object Reader:
    def ask[R]: Reader[R, R] = r => r

  extension [R, A](self: Reader[R, A]) def run = self.apply

  given [R]: Monad[[A] =>> Reader[R, A]] = new:
    def pure[A](a: A): Reader[R, A] = r => a
    extension [A](self: Reader[R, A])
      def flatMap[B](f: A => Reader[R, B]): Reader[R, B] =
        r => f(self(r))(r)

end ReaderAlias

// import ReaderRecord.*
import ReaderAlias.{ *, given }

case class User(
    firstName: String,
    lastName: String,
    email: String,
  )

val userNameShort: Reader[User, String] =
  Reader.ask[User].map(_.firstName)

val userNameLong: Reader[User, String] =
  Reader.ask[User].map(u => s"${u.firstName} ${u.lastName}")

val userEmail: Reader[User, String] =
  Reader.ask[User].map(_.email)

val userDetails =
  for
    shortName <- userNameShort
    longName  <- userNameLong
    email     <- userEmail
  yield s"Short name: $shortName, long name: $longName, email: $email"

@main
def readerMonadMain =
  val john = User("John", "Doe", "john.doe@email.com")

  val result = userDetails.run(john)
  println(s"RESULT: $result")
