package com.mehmetyucel.bitrise.dependency

import com.typesafe.config.ConfigFactory

object BitriseConfig {

  private      val config = ConfigFactory.load( )
  private lazy val root   = config.getConfig( "bitrise" )

  object Http {
    lazy val host = root.getString( "http.host" )
    lazy val port = root.getInt( "http.port" )
  }

}