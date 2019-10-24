package com.mehmetyucel.bitrise.service.booking

import com.mehmetyucel.bitrise.BookingSuccess
import com.mehmetyucel.bitrise.data.{OccupiedSeat, Plane}
import com.mehmetyucel.bitrise.request.{BookRequest, UnBookingRequest}
import com.mehmetyucel.bitrise.service.BaseService
import io.swagger.annotations._
import javax.ws.rs.Path


trait UnBookSeat extends BaseService {

  @Path("/unBook")
  @ApiOperation(value = "un-book a specific seat",
    nickname = "unBookSeat",
    httpMethod = "DELETE",
    response = classOf[BookingSuccess]
  )
  @ApiImplicitParams( Array(
    new ApiImplicitParam(
      required = true,
      dataTypeClass = classOf[UnBookingRequest],
      paramType = "body"
    )
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return the status of the Seat", response = classOf[BookingSuccess])
  ))
  def getUnBookRoute = {
    path("flight" / "unBook") {
      entity(as[UnBookingRequest]) { request =>
          delete {
            dependencies.plane.unBook(request.row, Plane.seatPositions(request.position))
            complete(successMessage("Booking removed successfully"))
          }
      }
    }
  }
}
