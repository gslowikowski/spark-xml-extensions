/*
 * Copyright 2020 Grzegorz Slowikowski (gslowikowski at gmail dot com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.databricks.spark.xml

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger

object FromXmlTestJob {

  def main(args: Array[String]): Unit = {

    val schema = "struct<rate: string>"

    val spark = SparkSession
      .builder
      .appName("from_xml expression test")
      .master("local[1]")
      .config("spark.driver.host", "localhost")
      .getOrCreate

    XmlExtensions.register()

    spark
      .readStream
      .format("rate")
      .load
      .selectExpr("""concat("<?xml version='1.0' encoding='UFT-8'?><root><rate>", value, "</rate></root>") as xml""")
      .selectExpr(s"from_xml(xml, '$schema') as extract")
      .selectExpr("extract.rate")
      .writeStream
      .format("console")
      .option("checkpointLocation", "target/checkpoints")
      .trigger(Trigger.ProcessingTime("5 seconds"))
      .start()
      .awaitTermination()

    XmlExtensions.unregister()
  }
}
