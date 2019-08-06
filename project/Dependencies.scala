import sbt._

object Version {
  val circe    = "0.11.1"
  val akka     = "2.5.23"
  val akkaHttp = "10.1.8"
}

object Dependencies {

  object circe {
    val core    = "io.circe" %% "circe-core"    % Version.circe
    val generic = "io.circe" %% "circe-generic" % Version.circe
    val parser  = "io.circe" %% "circe-parser"  % Version.circe
    val java8   = "io.circe" %% "circe-java8"   % Version.circe
    val literal = "io.circe" %% "circe-literal" % Version.circe
    val yaml    = "io.circe" %% "circe-yaml"    % "0.9.0"

    val json = List(core, generic, parser, java8)
    val all  = List(core, generic, parser, java8, literal, yaml)
  }

  object akka {
    val actor   = "com.typesafe.akka" %% "akka-actor"   % Version.akka
    val cluster = "com.typesafe.akka" %% "akka-cluster" % Version.akka
    val stream  = "com.typesafe.akka" %% "akka-stream"  % Version.akka

    val http      = "com.typesafe.akka" %% "akka-http"       % Version.akkaHttp
    val httpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.27.0"

    val testkit       = "com.typesafe.akka" %% "akka-testkit"        % Version.akka     % "test"
    val streamTestkit = "com.typesafe.akka" %% "akka-stream-testkit" % Version.akka     % "test"
    val httpTestkit   = "com.typesafe.akka" %% "akka-http-testkit"   % Version.akkaHttp % "test"

    val allHttp = List(http, httpCirce, httpTestkit)
  }

  val cats    = "org.typelevel" %% "cats"    % "1.6.0"
  val monix   = "io.monix"      %% "monix"   % "3.0.0-RC3"
  val decline = "com.monovore"  %% "decline" % "0.5.0"

  val betterFiles = "com.github.pathikrit" %% "better-files" % "3.7.1"
  val scalajHttp  = "org.scalaj"           %% "scalaj-http"  % "2.4.1"
  val pureConfig  = "com.github.melrief"   %% "pureconfig"   % "0.10.2"
  val pprint      = "com.lihaoyi"          %% "pprint"       % "0.5.4"
  val scalaTest   = "org.scalatest"        %% "scalatest"    % "3.0.1" % "test"
  val scalaCheck  = "org.scalacheck"       %% "scalacheck"   % "1.13.4" % "test"

}
