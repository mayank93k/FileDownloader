name := "Project_Spark_Scala"

version := "0.1"

scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.5.1",
  "com.typesafe" % "config" % "1.4.3",
  "org.apache.spark" %% "spark-sql" % "3.5.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "com.google.cloud.spark" %% "spark-bigquery-with-dependencies" % "0.39.0",
  "org.scalatest" %% "scalatest" % "3.2.19"
)

excludeDependencies ++= Seq(ExclusionRule("ch.qos.logoback", "logoback-classic"))