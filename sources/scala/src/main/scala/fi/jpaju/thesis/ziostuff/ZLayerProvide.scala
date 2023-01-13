package fi.jpaju.thesis.ziostuff
package zlayers

import zio.*

// ZLayer.make and ZIO.provide resolve the dependency graph
val useD: ZIO[D, Nothing, Int] = ZIO.service[D].as(34)

val layer: ZLayer[Any, Nothing, D] =
  ZLayer.make[D](layerA, layerB, layerC, layerD)

val provided1: ZIO[Any, Nothing, Int] = useD.provideLayer(layer)
val provided2: ZIO[Any, Nothing, Int] = layer(useD) // layer.apply
val provided3: ZIO[Any, Nothing, Int] =
  useD.provide(layerA, layerB, layerC, layerD)
