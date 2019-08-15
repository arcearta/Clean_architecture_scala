val baseName = "scala-clean"

mainClass in (Compile, run) := Some("com.co.QuickstartServer")
//mainClass in (Compile, run) := Some("sura.project.slik.MultiDBCakeExample")


lazy val akkaHttpVersion = "10.1.8"
lazy val akkaVersion     = "2.5.22"


val akkaLibraries: Seq[ModuleID] = Seq(
  "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
  "com.google.inject" % "guice"                 % "3.0",
  "org.scaldi"        %% "scaldi-akka"          % "0.5.8",
  "com.github.j5ik2o" %% "scala-ddd-base-core"  % "1.0.13",
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",

  "org.slf4j" % "slf4j-nop" % "1.7.10",
  "com.h2database" % "h2" % "1.4.187",
  "org.xerial" % "sqlite-jdbc" % "3.8.7",
  "com.oracle" % "ojdbc7" % "12.1.0.2" from "file:///D:/configuracionServidores/dllo/librerias/ojdbc/ojdbc7-12.1.0.2.jar",
 // "com.oracle" % "ojdbc7" % "12.1.0.2",
  "org.mongodb" %% "casbah" % "3.1.1",

  //"com.rxthings" %% "akka-injects" % "0.8",
  "net.codingwell" %% "scala-guice" % "4.2.4",
  "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-testkit"         % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion % Test,
  "org.scalatest"     %% "scalatest"            % "3.0.5" % Test,
  "com.typesafe.slick" %% "slick-testkit" % "3.2.1" % "test",
  "com.typesafe.slick" %% "slick" % "3.3.0" % Test,
  "com.novocode" % "junit-interface" % "0.10" % "test",
  "ch.qos.logback" % "logback-classic" % "0.9.28" % "test"

)


lazy val entities = (project in file("domain/model"))
  .settings(
    name := s"$baseName-entities"
  )

lazy val usecases = (project in file("domain/usecases"))
  .settings(
    name := s"$baseName-usecases",
    libraryDependencies ++= akkaLibraries
  )
  .dependsOn(entities)

lazy val adapters = (project in file("infraestructure/driven_adapters"))
  .settings(
    name := s"$baseName-adapters",
    libraryDependencies ++= akkaLibraries
  )
  //.settings(coreSettings, libraryDependencies ++= akkaLibraries)
  .dependsOn(entities, usecases)

lazy val entry_points = (project in file("infraestructure/entry_points"))
  .settings(
    name := s"$baseName-entry_points",
    libraryDependencies ++= akkaLibraries
  )
  .dependsOn(adapters)
//.settings(coreSettings)

lazy val main = (project in file("applications/app_service"))
  .settings(
    name := s"$baseName-main",
    libraryDependencies ++= akkaLibraries
  )
  //.settings(coreSettings, libraryDependencies ++= akkaLibraries)
  .dependsOn(entities)

lazy val root = (project in file("."))
  .settings(
    name := baseName,
    libraryDependencies ++= akkaLibraries
  ).dependsOn(entry_points, adapters, entities)
  //.settings(coreSettings, libraryDependencies ++= akkaLibraries)
  /*.aggregate(
    usecases,
    entry_points,
    main
  )*/


