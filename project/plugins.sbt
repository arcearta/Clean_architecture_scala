resolvers ++= Seq(
  "Sura" at "http://srintcp01:8081/artifactory/sura-repo/"
 /* "jw3 at bintray" at "https://dl.bintray.com/jw3/maven",
  "guice" at "https://mvnrepository.com/artifact/net.codingwell/scala-guice",
  "Sonatype OSS Snapshot Repository" at "https://oss.sonatype.org/content/gateway.repositories/snapshots/",
  "Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/gateway.repositories/releases/",
  "Seasar Repository" at "http://maven.seasar.org/maven2/",
  "Flyway" at "https://davidmweber.github.io/flyway-sbt.repo"*/

)


addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.15")
