package com.databricks.spark.xml

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.FunctionIdentifier
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.DataType

import scala.io.Source

object FromXmlTestJob {

  def main(args: Array[String]): Unit = {

    val jsonSchemaUrl = this.getClass.getClassLoader.getResource("rate-schema.json")
    val schemaAsJson = Source.fromURL(jsonSchemaUrl, "UTF-8").getLines.mkString
    val schemaAsDDL = DataType.fromJson(schemaAsJson).sql

    val spark = SparkSession
      .builder
      .appName("from_xml expression test")
      .master("local[1]")
      .config("spark.driver.host", "localhost")
      .getOrCreate

    spark.sessionState.functionRegistry.createOrReplaceTempFunction(
      "from_xml", com.databricks.spark.xml.XmlToStructs)

    spark
      .readStream
      .format("rate")
      .load
      .selectExpr("""concat("<?xml version='1.0' encoding='UFT-8'?><root><rate>", value, "</rate></root>") as xml""")
      .selectExpr(s"from_xml(xml, '$schemaAsDDL') as extract")
      .selectExpr("extract.rate")
      .writeStream
      .format("console")
      .trigger(Trigger.ProcessingTime("5 seconds"))
      .start()
      .awaitTermination()

    spark.sessionState.functionRegistry.dropFunction(FunctionIdentifier("from_xml"))
  }
}
