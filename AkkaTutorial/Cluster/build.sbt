name := "Cluster"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.3",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.3" % Test,
  "com.typesafe.akka" %% "akka-cluster" % "2.5.3",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.5.3",
  "com.typesafe.akka" %% "akka-distributed-data" % "2.5.3",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.3",
  "com.typesafe.akka" % "akka-cluster-metrics_2.12" % "2.5.3",
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
)