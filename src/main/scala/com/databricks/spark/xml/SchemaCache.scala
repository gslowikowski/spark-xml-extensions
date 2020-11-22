package com.databricks.spark.xml

import org.apache.spark.sql.catalyst.expressions.{ExprUtils, Expression}
import org.apache.spark.sql.types.DataType

import scala.collection.mutable

object SchemaCache {

  private val schemaCache: mutable.Map[Expression, DataType] = mutable.Map[Expression, DataType]()

  def get(expr: Expression): DataType = schemaCache.getOrElseUpdate(expr, ExprUtils.evalTypeExpr(expr))

}
