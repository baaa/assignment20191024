package com.mehmetyucel.bitrise.request

import com.mehmetyucel.bitrise.data.{Person, SeatPosition}

object ApiRequests {

}

case class BookRequest(row: Int, position: String, person: Person)
case class BookBestRequest(seatClass: String, position: String, person: Person)
case class UnBookingRequest(row: Int, position: String)
case class AmendRequest(fromRow: Int, fromPosition: String, toRow: Int, toPosition: String)