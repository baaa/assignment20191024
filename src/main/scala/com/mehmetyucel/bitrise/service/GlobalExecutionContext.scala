package com.mehmetyucel.bitrise.service

import scala.concurrent.ExecutionContext

trait GlobalExecutionContext {
  implicit val ex = ExecutionContext.Implicits.global
}