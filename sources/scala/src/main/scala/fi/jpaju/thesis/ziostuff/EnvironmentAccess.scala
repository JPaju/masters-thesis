package fi.jpaju.thesis.ziostuff
package environmentaccess

import zio.*

// Same as 'ask' in reader monad
val ask: ZIO[String, Nothing, String] =
  ZIO.service[String]

// Eqivalent to: ZIO.service[String].map(_.length)
val askAndMap: ZIO[String, Nothing, Int] =
  ZIO.serviceWith[String](_.length)

// Equivalent to: ZIO.service[Random].flatMap(_.nextInt)
val askAndFlatMap: ZIO[Random, Nothing, Int] =
  ZIO.serviceWithZIO[Random](_.nextInt)
