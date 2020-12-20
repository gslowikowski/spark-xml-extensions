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

import org.apache.spark.sql.catalyst.expressions.{ExprUtils, Expression}
import org.apache.spark.sql.types.DataType

import scala.collection.mutable

/**
 * Spark schema cache
 */
object SchemaCache {

  private val schemaCache: mutable.Map[Expression, DataType] = mutable.Map[Expression, DataType]()

  def get(expr: Expression): DataType = schemaCache.getOrElseUpdate(expr, ExprUtils.evalTypeExpr(expr))

}
