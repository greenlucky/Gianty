name := """play-restfull-book"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += javaJpa

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
