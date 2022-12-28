package fi.jpaju.thesis.ziostuff
package forking_operators

import zio.{ Fiber, Scope, URIO }

trait ZIO[-R, +E, +A]:
  def fork: URIO[R, Fiber[E, A]]
  def forkDaemon: URIO[R, Fiber[E, A]]
  def forkScoped: URIO[R & Scope, Fiber[E, A]]
  def forkIn(scope: Scope): URIO[R, Fiber[E, A]]
