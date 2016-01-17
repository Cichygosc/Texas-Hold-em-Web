name := "websocket-logger"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

javacOptions += "-Xlint:deprecation"     

libraryDependencies ++= Seq(
  "org.webjars" % "bootstrap" % "3.3.4",
  "org.webjars" % "jquery" % "2.1.4"
)
