package com.mehmetyucel.bitrise


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Directives, ExceptionHandler, RouteConcatenation}
import akka.stream.ActorMaterializer
import com.mehmetyucel.bitrise.dependency.{BitriseConfig, Dependencies}
import com.typesafe.scalalogging.LazyLogging
import ch.megard.akka.http.cors.CorsDirectives._
import com.mehmetyucel.bitrise.service.FlightService
import com.mehmetyucel.bitrise.service.swagger.{DocumentationService, SwaggerDocService}

object BookingApp extends App with Api with Web with LazyLogging {
  logger.info( "started company api app" )
}


trait Api extends RouteConcatenation with LazyLogging {
  implicit lazy val system = ActorSystem( "booking-api-system" )
  implicit      val dp     = system.dispatcher
  val dependencies = new Dependencies( )
  sys.addShutdownHook( system.terminate( ) )

  implicit def myExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: Exception => Directives.complete(e)
  }

  val routes = {
    logger.info( "reading routes" )
    cors( )(
      new FlightService( dependencies ).route ~
        SwaggerDocService.routes ~
        new DocumentationService( ).routes
    )
  }
}


trait Web {
  this: Api =>
  implicit val materializer = ActorMaterializer( )
  Http( ).bindAndHandle( routes, BitriseConfig.Http.host, BitriseConfig.Http.port )
}

/*
  val p = Plane(5 , 10)

  println(1)
  p.book(2, Center, Person("mehmet", "15/15/1915", "PP3579", "CN"))
  println(p)

  println(2)
  val v = p.bestSeat(Business, Center, Person("mehmet 2", "15/15/1915", "PP3579", "CN"))
  println(p)

  println(3)
  p.bestSeat(Business, Center, Person("mehmet 3", "15/15/1915", "PP3579", "CN"))
  println(p)

  println(4)
  p.bestSeat(Economy, Window, Person("mehmet 3", "15/15/1915", "PP3579", "CN"))
  println(p)

  */
