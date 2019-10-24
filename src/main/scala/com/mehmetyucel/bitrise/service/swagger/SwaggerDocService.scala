package com.mehmetyucel.bitrise.service.swagger

import scala.reflect.runtime.{universe => ru}
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import com.github.swagger.akka.model.Info
import com.github.swagger.akka.SwaggerHttpService
import com.mehmetyucel.bitrise.dependency.BitriseConfig
import com.mehmetyucel.bitrise.service.FlightService
import io.swagger.models.auth.BasicAuthDefinition

//
//class SwaggerDocService(system: ActorSystem) extends SwaggerHttpService with HasActorSystem {
//  override implicit val actorSystem: ActorSystem = system
//  override implicit val materializer: ActorMaterializer = ActorMaterializer()
//  override val apiTypes = Seq(ru.typeOf[CompanyService])
//  override val host = ScarifGateConfiguration.Http.host + ":" + ScarifGateConfiguration.Http.port
//  override val info = Info(version = "1.0")
//  // override val externalDocs = Some(new ExternalDocs("Core Docs", "http://acme.com/docs"))
//  override val securitySchemeDefinitions = Map("basicAuth" -> new BasicAuthDefinition())
//  override val apiDocsPath: String = "swagger"
//}
//
//class DocumentationService extends Directives {
//  val routes =
//    path("docs") { getFromResource("dist/index.html") } ~
//      getFromResourceDirectory("dist")
//}


object SwaggerDocService extends SwaggerHttpService {
  override val apiClasses: Set[Class[_]] = Set(classOf[FlightService])
  override val host = "localhost" +":" + BitriseConfig.Http.port
  override val apiDocsPath = "swagger" //where you want the swagger-json endpoint exposed
  override val info = Info() //provides license and other description details
}

class DocumentationService extends Directives {
  val routes =
    path("docs") { getFromResource("dist/index.html") } ~
      getFromResourceDirectory("dist")
}