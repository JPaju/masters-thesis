Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / watchBeforeCommand    := Watch.clearScreen
ThisBuild / watchTriggeredMessage := Watch.clearScreenOnTrigger

// Nightly versions won't complain about experimental features
ThisBuild / scalaVersion := "3.2.1-RC1-bin-20220905-c93098b-NIGHTLY" // This is the lates 3.2.1 nightly build

ThisBuild / scalacOptions ++=
  Seq(
    "-source:future",
    "-deprecation",
    "-feature",
    "-unchecked",
    "-explain",
    "-Ysafe-init",
    "-Ykind-projector",
    "-Ycc", // Experimental capture checking
    "-Wconf:id=E029:error,msg=non-initialized:error,msg=spezialized:error,cat=unchecked:error",
  )
