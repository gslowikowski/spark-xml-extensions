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

import org.apache.spark.sql.catalyst.expressions.{ExpectsInputTypes, ExprUtils, Expression, UnaryExpression}
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenFallback
import org.apache.spark.sql.types.DataType

/**
 * Converts an XML input string to a [[StructType]]
 * with the specified schema.
 */
case class XmlToStructs(inputExpressions: Seq[Expression])
  extends UnaryExpression
    with CodegenFallback
    with ExpectsInputTypes {

  require(inputExpressions.size >= 2, "Too few parameters")
  require(inputExpressions.size <= 3, "Too many parameters")

  val delegate = XmlDataToCatalyst(
    inputExpressions(0),
    SchemaCache.get(inputExpressions(1)),
    if (inputExpressions.size > 2)
      XmlOptions(ExprUtils.convertToMapData(inputExpressions(2)))
    else
      XmlOptions(Map.empty[String, String])
  )

  override def dataType: DataType = delegate.dataType

  override def child: Expression = delegate.child

  override def nullSafeEval(input: Any): Any = delegate.nullSafeEval(input)

  override def inputTypes: Seq[DataType] = delegate.inputTypes

  override def prettyName: String = "from_xml"

}


