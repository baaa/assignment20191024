package com.mehmetyucel.bitrise

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes
import com.mehmetyucel.bitrise.data.Person

trait ApiResponse extends JsonFormats {

  def successMessage(result: String): ToResponseMarshallable = {
    ToResponseMarshallable(StatusCodes.OK ->  BookingSuccess(StatusCodes.OK.intValue, result))
  }

  def error(message: String): ToResponseMarshallable = {
    ToResponseMarshallable(
      StatusCodes.InternalServerError -> BookingError(
        StatusCodes.InternalServerError.intValue, message
      )
    )
  }

  def notFound(id: String): ToResponseMarshallable = {
    ToResponseMarshallable(
      StatusCodes.NotFound,
      BookingError(StatusCodes.NotFound.intValue, "not found")
    )
  }
}

case class BookingSuccess(httpStatus: Int, message: String)

case class BookingError(httpStatus: Int, message: String)

case class Booking(row: Int, rowClass: String, position: String, person: Person)