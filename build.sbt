organization := "com.github.gslowikowski"

name := "spark-xml-extensions"

scalaVersion := "2.12.10"

crossScalaVersions := Seq("2.12.10")

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "com.databricks" %% "spark-xml" % "0.10.0" % Provided,
  "org.apache.spark" %% "spark-sql" % "3.0.0" % Provided
)

publishMavenStyle := true

publishArtifact in Test := false

pomExtra := {
  <inceptionYear>2020</inceptionYear>
  <url>https://github.com/gslowikowski/spark-xml-extensions</url>
    <licenses>
      <license>
        <name>Apache License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>https://github.com/gslowikowski/spark-xml-extensions</url>
      <connection>scm:git:git@github.com:gslowikowski/spark-xml-extensions.git</connection>
    </scm>
    <developers>
      <developer>
        <id>gslowikowski</id>
        <name>Grzegorz Slowikowski</name>
        <email>gslowikowski@gmail.com</email>
        <roles>
          <role>Developer</role>
        </roles>
        <timezone>+1</timezone>
      </developer>
    </developers>
}

pomIncludeRepository := {
  _ => false
}

publishTo := {
  if (isSnapshot.value)
    Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
    Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value
