package fi.jpaju.thesis.ziostuff

import zio.*

import java.net.URL

def fetchContent(url: URL): IO[Throwable, String] = ???
val urls: List[URL]                               = ???

val contents: IO[Throwable, List[String]] =
  ZIO.foreachPar(urls)(fetchContent)

// By default all requests are performed in parallel
val unboundedParallelism = contents

// The parallelism is limited to 10 concurrent requests
val boundedParallelism = unboundedParallelism.withParallelism(10)

// Bounded parallelism can be converted back to unbounded
val unboundedAgain = boundedParallelism.withParallelismUnbounded
