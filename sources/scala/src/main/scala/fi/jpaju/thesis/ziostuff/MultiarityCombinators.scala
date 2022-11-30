package fi.jpaju.thesis.ziostuff

import zio.*

type Result = Nothing

def findById(id: Int): UIO[Result]                  = ???
def combineResults(total: Int, result: Result): Int = ???

val ids = List(1, 2, 3)

val found1: UIO[List[Result]] = ZIO.foreach(ids)(findById(_))
val found2: UIO[List[Result]] = ZIO.collectAll(ids.map(findById(_)))

val combined: UIO[Int] =
  ZIO.mergeAll(ids.map(findById(_)))(0)(combineResults(_, _))
