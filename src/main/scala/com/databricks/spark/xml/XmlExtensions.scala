package com.databricks.spark.xml

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.FunctionIdentifier

/**
 * Spark SQL extensions registry
 */
object XmlExtensions {

  val fromXmlFuncName = "from_xml"

  def register(): Unit = register(SparkSession.active)

  def register(spark: SparkSession): Unit = {
    spark.sessionState.functionRegistry.createOrReplaceTempFunction(
      fromXmlFuncName, com.databricks.spark.xml.XmlToStructs)
  }

  def unregister(): Unit = unregister(SparkSession.active)

  def unregister(spark: SparkSession): Unit = {
    spark.sessionState.functionRegistry.dropFunction(FunctionIdentifier(fromXmlFuncName))
  }
}
