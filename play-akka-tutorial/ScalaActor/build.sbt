name := "ScalaActor"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.3",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % Test,
  "com.typesafe.akka" %% "akka-cluster" % "2.5.3",
  "com.typesafe.akka" %% "akka-cluster-sharding" %  "2.5.3",
  "com.typesafe.akka" %% "akka-distributed-data" % "2.5.3",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.3",
  "com.typesafe.akka" % "akka-remote_2.11" % "2.5.3"
)