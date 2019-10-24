package com.mehmetyucel.bitrise.service.booking

import akka.http.scaladsl.model.StatusCodes
import com.mehmetyucel.bitrise.data.{OccupiedSeat, Plane, Seat}
import com.mehmetyucel.bitrise.request.ApiRequests
import com.mehmetyucel.bitrise.service.BaseService
import io.swagger.annotations._
import javax.ws.rs.Path


trait Inspect extends BaseService {
  @Path("/inspect")
  @ApiOperation(value = "Inspect a specific seat",
    nickname = "inspectSeat",
    httpMethod = "GET",
    response = classOf[OccupiedSeat]
  )
  @ApiImplicitParams( Array(
    new ApiImplicitParam(
      name = "row",
      required = true,
      dataType = "integer",
      paramType = "query" ),
    new ApiImplicitParam(
      name = "seatPosition",
      required = true,
      dataType = "string",
      allowableValues = "Window,Center,Aisle",
      paramType = "query" )
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return the status of the Seat", response = classOf[OccupiedSeat])
  ))
  def getInspectRoute = {
    path("flight" / "inspect") {
      parameters( 'row.as[Int], 'seatPosition) { (row, seatPosition) =>
        get {
          complete(
            StatusCodes.OK -> dependencies.plane.inspect(row, Plane.seatPositions(seatPosition))
          )
        }
      }
    }
  }
}
