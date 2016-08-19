lazy val deployToWeb = taskKey[Unit]("deploys generated js files to the web directory")
def deployToWebTask = Def.task {
  val fs = crossTarget.value * "*.js*"
  fs.get.foreach(f => IO.copyFile(f, baseDirectory.value.getParentFile / "web" / f.name))
}

lazy val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8",
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  ),
  deployToWeb := deployToWebTask.value
)

lazy val root = (project in file("."))
    .aggregate(doorbell, ringer, service)

lazy val doorbell = project
    .enablePlugins(ScalaJSPlugin)
    .settings(commonSettings: _*)
    .settings(
      name := "doorbell",
      libraryDependencies ++= Seq(
        "com.lihaoyi" %%% "scalatags" % "0.6.0"
      )
    )

lazy val ringer = project
    .enablePlugins(ScalaJSPlugin)
    .settings(commonSettings: _*)
    .settings(
      name := "ringer",
      libraryDependencies ++= Seq(
        "com.lihaoyi" %%% "scalatags" % "0.6.0"
      )
    )

lazy val service = project
  .enablePlugins(ScalaJSPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "service",
    libraryDependencies ++= Seq(

    ),
    scalaJSOutputWrapper := ("", "vdb.service.ServiceMain().main();")
  )