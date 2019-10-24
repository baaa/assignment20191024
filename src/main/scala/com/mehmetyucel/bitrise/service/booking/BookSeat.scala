package com.mehmetyucel.bitrise.service.booking

import akka.http.scaladsl.model.StatusCodes
import com.mehmetyucel.bitrise.{Booking, BookingSuccess}
import com.mehmetyucel.bitrise.data.{OccupiedSeat, Person, Plane}
import com.mehmetyucel.bitrise.request.BookRequest
import com.mehmetyucel.bitrise.service.BaseService
import io.swagger.annotations._
import javax.ws.rs.Path


trait BookSeat extends BaseService {

  @Path("/book")
  @ApiOperation(value = "Book a specific seat",
    nickname = "bookSeat",
    httpMethod = "POST",
    response = classOf[Booking]
  )
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      required = true,
      dataTypeClass = classOf[BookRequest],
      paramType = "body"
    )
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return the status of the Seat", response = classOf[Booking])
  ))
  def getBookingRoute = {
    path("flight" / "book") {
      entity(as[BookRequest]) { request =>
        post {
          dependencies.plane.book(request.row, Plane.seatPositions(request.position), request.person) match {
            case Left(message) => complete(error(message))
            case Right(booking) => complete(StatusCodes.OK, booking)
          }
        }
      }
    }
  }
}
