package com.mehmetyucel.bitrise.service.booking

import akka.http.scaladsl.model.StatusCodes
import com.mehmetyucel.bitrise.data.{OccupiedSeat, Plane}
import com.mehmetyucel.bitrise.request.BookBestRequest
import com.mehmetyucel.bitrise.service.BaseService
import com.mehmetyucel.bitrise.{Booking, BookingError, BookingSuccess}
import io.swagger.annotations._
import javax.ws.rs.Path


trait BookBestSeat extends BaseService {

  @Path("/bookBest")
  @ApiOperation(value = "Book the best available seat for preferences",
    nickname = "bestSeat",
    httpMethod = "POST",
    response = classOf[Booking]
  )
  @ApiImplicitParams(Array(
    new ApiImplicitParam(
      required = true,
      dataTypeClass = classOf[BookBestRequest],
      paramType = "body"
    )
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Return the status of the Seat", response = classOf[Booking]),
    new ApiResponse(code = 500, message = "Something went wrong", response = classOf[BookingError])
  ))
  def getBestSeatRoute = {
    path("flight" / "bookBest") {
      entity(as[BookBestRequest]) { request =>
        post {
          dependencies.plane.bestSeat(Plane.rowClasses(request.seatClass), Plane.seatPositions(request.position), request.person) match {
            case Left(message) => complete(error(message))
            case Right(result) => complete(StatusCodes.OK -> result)
          }
        }
      }
    }
  }
}
