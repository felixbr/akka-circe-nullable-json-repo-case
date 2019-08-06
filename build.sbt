lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(
        scalaVersion := "2.12.9"
      )
    ),
    name := """akka-circe-repo-case""",
    version := "0.1.0",
    libraryDependencies ++= List(
      Dependencies.akka.stream,
      Dependencies.akka.http,
      Dependencies.akka.httpCirce,
      Dependencies.akka.testkit,
      Dependencies.akka.streamTestkit,
      Dependencies.akka.httpTestkit,
      Dependencies.scalaTest,
      Dependencies.scalaCheck
    ) ++ Dependencies.circe.all
  )
  .enablePlugins(JavaAppPackaging)

scalacOptions ++= List( // useful compiler flags for scala
  "-deprecation",
  "-encoding",
  "UTF-8", // yes, this is 2 args
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint:_",
  "-Ywarn-unused:-imports",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Ypartial-unification"
)
