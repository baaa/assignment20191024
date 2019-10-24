package com.mehmetyucel.bitrise.service

import com.mehmetyucel.bitrise.dependency.Dependencies
import com.mehmetyucel.bitrise.service.booking._
import io.swagger.annotations._
import javax.ws.rs.Path


@Api(value = "/flight", produces = "application/json")
@Path("/flight")
class FlightService(override val dependencies: Dependencies)
  extends FlightInfo
    with Inspect
    with BookSeat
    with UnBookSeat
    with AmendSeat
    with BookBestSeat {
  val route = getFlightInfoRoute ~
    getInspectRoute ~
    getBookingRoute ~
    getUnBookRoute ~
    getAmendRoute ~
    getBestSeatRoute
}

