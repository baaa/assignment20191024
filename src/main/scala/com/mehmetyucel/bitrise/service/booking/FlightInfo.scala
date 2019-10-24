package com.mehmetyucel.bitrise.service.booking

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes
import com.mehmetyucel.bitrise.data.{Person, Row}
import io.swagger.annotations._
import javax.ws.rs.Path
import com.mehmetyucel.bitrise.service.BaseService


trait FlightInfo extends BaseService {
  @Path("/info")
  @ApiOperation(value = "Flight Info",
    notes = "Get the status of all seats in flight",
    nickname = "flightInfo",
    httpMethod = "GET",
    response = classOf[Vector[Row]]
  )
  @ApiImplicitParams(Array(

  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return plane information", response = classOf[Vector[Row]])
  ))
  def getFlightInfoRoute = {
    path("flight" / "info") {
      get {
        complete(StatusCodes.OK -> dependencies.plane.seating)
      }
    }
  }
}
