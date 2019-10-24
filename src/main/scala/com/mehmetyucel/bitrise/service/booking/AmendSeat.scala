package com.mehmetyucel.bitrise.service.booking

import akka.http.scaladsl.model.StatusCodes
import com.mehmetyucel.bitrise.{Booking, BookingError}
import com.mehmetyucel.bitrise.data.Plane
import com.mehmetyucel.bitrise.request.AmendRequest
import com.mehmetyucel.bitrise.service.BaseService
import io.swagger.annotations._
import javax.ws.rs.Path


trait AmendSeat extends BaseService {

  @Path("/amend")
  @ApiOperation(value = "move a passenger from one seat to another",
    nickname = "amendSeat",
    httpMethod = "PUT",
    response = classOf[Booking]
  )
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      required = true,
      dataTypeClass = classOf[AmendRequest],
      paramType = "body"
    )
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return the status of the Seat", response = classOf[Booking]),
    new ApiResponse(code = 500, message = "Something went wrong", response = classOf[BookingError])
  ))
  def getAmendRoute = {
    path("flight" / "amend") {
      entity(as[AmendRequest]) { request =>
        put {
          dependencies.plane.movePassenger(request.fromRow, Plane.seatPositions(request.fromPosition), request.toRow, Plane.seatPositions(request.toPosition)) match {
            case Left(message) => complete(error(message))
            case Right(result) => complete(StatusCodes.OK, result)
          }
        }
      }
    }
  }
}
