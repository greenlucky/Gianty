name := "httpwebsocketclient"

version := "1.0"

scalaVersion := "2.11.11"

libraryDependencies ++= {
  var akkaVersion = "2.4.17"
  Seq(
    "com.typesafe.akka" %% "akka-http" % "10.0.6",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.6" % Test,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
    "com.typesafe.akka" % "akka-slf4j_2.11" % akkaVersion
  )

}
