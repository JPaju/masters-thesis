package fi.jpaju.thesis.ziostuff
package retries

import zio.*

import java.io.*

val readFile: IO[Throwable, Array[Byte]] =
  ZIO.attempt(new FileInputStream("file.txt").readAllBytes())

val retryForever: UIO[Array[Byte]]             = readFile.eventually
val retryFiveTimes: IO[Throwable, Array[Byte]] = readFile.retryN(5)

val retryUnlessFileNotFound: IO[Throwable, Array[Byte]] =
  readFile.retryUntil {
    case _: FileNotFoundException => true
    case _                        => false
  }
