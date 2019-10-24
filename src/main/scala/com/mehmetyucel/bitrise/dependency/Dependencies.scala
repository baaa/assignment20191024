package com.mehmetyucel.bitrise.dependency

import com.mehmetyucel.bitrise.data.{Person, Plane, Window}
import com.typesafe.scalalogging.LazyLogging

class Dependencies extends LazyLogging {
  lazy val plane = Plane(5, 10)
}


trait HasDependencies {
  val dependencies : Dependencies
}