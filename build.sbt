name := """play-quandl-metals"""

organization := "alepro"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
"com.typesafe.play" % "play_2.11" % "2.5.12",
"org.scalatest" %% "scalatest" % "2.2.4" % "test",
"org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
"org.mockito" % "mockito-all" % "1.10.19" % Test
)

EclipseKeys.withSource := true