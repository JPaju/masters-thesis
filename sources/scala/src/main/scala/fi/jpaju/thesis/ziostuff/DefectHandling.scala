package fi.jpaju.thesis.ziostuff
package defects

import zio.*

import java.io.FileInputStream
import java.io.FileNotFoundException

val readFile: IO[Throwable, Array[Byte]] =
  ZIO.attempt(new FileInputStream("file.txt").readAllBytes())

val allErrorsToDefects: IO[Nothing, Array[Byte]] = readFile.orDie

val someErrorsToDefects: IO[FileNotFoundException, Array[Byte]] =
  readFile.refineToOrDie[FileNotFoundException]

val allDefectsToErrors: IO[Throwable, Array[Byte]] =
  allErrorsToDefects.resurrect

val someDefectsToFailure: IO[FileNotFoundException, Array[Byte]] =
  allErrorsToDefects.unrefineTo[FileNotFoundException]
