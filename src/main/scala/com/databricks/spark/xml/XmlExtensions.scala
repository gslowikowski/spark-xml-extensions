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
