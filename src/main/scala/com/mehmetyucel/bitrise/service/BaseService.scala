package com.mehmetyucel.bitrise.service

import akka.http.scaladsl.server.Directives
import com.mehmetyucel.bitrise.{ApiResponse, JsonFormats}
import com.mehmetyucel.bitrise.dependency.HasDependencies

trait BaseService extends Directives
  with JsonFormats
  with GlobalExecutionContext
  with HasDependencies
  with ApiResponse