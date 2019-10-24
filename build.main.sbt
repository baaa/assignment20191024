name := "bitrise"

resolvers ++= Seq(
  Resolver.sonatypeRepo("public"),
  Resolver.sonatypeRepo("snapshots"),
)

organization := "com.mehmetyucel"
version := "0.0.1"
scalaVersion := "2.12.8"

val akkaHttpVersion = "10.1.8"
val akkaVersion = "2.5.22"

val compileDependencies = Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-json4s" % "1.17.0",

//  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
//  "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.8.2",
//  "com.github.swagger-akka-http" %% "swagger-akka-http" % "2.0.4",
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "1.1.0",
  "ch.megard" %% "akka-http-cors" % "0.1.10",
  "org.json4s" %% "json4s-native" % "3.5.0",



  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
).map(_.excludeAll(ExclusionRule("org.scalatest")))

val testDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.0.0" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.mockito" % "mockito-all" % "1.10.19" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion
)

scalacOptions += "-feature"
scalacOptions += "-deprecation"
libraryDependencies ++= compileDependencies ++ testDependencies

parallelExecution in Test := false

test in assembly := {}