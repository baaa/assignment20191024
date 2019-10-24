package com.mehmetyucel.bitrise.data

import com.mehmetyucel.bitrise.Booking

sealed trait Seat
case object FreeSeat extends Seat
final case class OccupiedSeat(passenger: Person) extends Seat

sealed abstract class SeatPosition(val name: String)
case object Window extends SeatPosition("Window")
case object Center extends SeatPosition("Center")
case object Aisle extends SeatPosition("Aisle")

sealed abstract class RowClass(val name: String)
case object Business extends RowClass("Business")
case object Economy extends RowClass("Economy")

case class Person(name: String, dateOfBirth: String, passportNumber: String, nationality: String)

case class Row(row: Int, rowClass: RowClass, seats: Map[String,Seat])

object Plane {

  val seatPositions: Map[String, SeatPosition] = Map(
    Window.name -> Window,
    Center.name -> Center,
    Aisle.name -> Aisle,
  )

  val rowClasses: Map[String, RowClass] = Map(
    Business.name -> Business,
    Economy.name -> Economy
  )

  def apply(businessRowCount: Int, economyRowCount: Int): Plane = {
    val business = (1 to businessRowCount).foldLeft( Vector[Row]()) { (acc, it) =>
      acc :+ Row( it, Business, Map(Window.name -> FreeSeat, Center.name -> FreeSeat, Aisle.name -> FreeSeat))
    }

    val economy = (businessRowCount +1 to businessRowCount+ economyRowCount).foldLeft( Vector[Row]()) { (acc, it) =>
      acc :+ Row( it, Economy, Map(Window.name -> FreeSeat, Center.name -> FreeSeat, Aisle.name -> FreeSeat) )
    }

    new Plane(business ++ economy)
  }
}

class Plane(var seating: Vector[Row]) {

  private def isFree(row: Int, position: SeatPosition): Boolean = {
    seating(row-1).seats(position.name) match {
      case FreeSeat => true
      case _ => false
    }
  }

  def inspect(row: Int, position: SeatPosition): Seat = {
    seating(row-1).seats(position.name)
  }


  def book(row: Int, position:SeatPosition, person: Person) : Either[String, Booking] =  {
    if(isFree(row, position)) {
      val newSeating = seating(row-1).seats.updated( position.name, OccupiedSeat(person))
      seating = seating.updated(row-1, Row(row, seating(row-1).rowClass, newSeating))
      Right(Booking(row, seating(row-1).rowClass.name, position.name, person))
    } else {
      Left("Requested seat already occupied")
    }
  }

  def unBook(row: Int, position: SeatPosition) : Unit = {
    val newSeating = seating(row-1).seats.updated(position.name, FreeSeat)
    seating = seating.updated(row-1, Row(row, seating(row).rowClass, newSeating))
  }

  def movePassenger(fromRow: Int, fromPosition: SeatPosition, toRow: Int, toPosition: SeatPosition): Either[String, Booking] = {
    inspect(fromRow, fromPosition) match {
      case OccupiedSeat(person) =>
        book(toRow, toPosition, person) match {
          case Right(r) =>
            unBook(fromRow, fromPosition)
            Right(r)
          case Left(message) => Left(message)
        }
      case FreeSeat =>
        Left("Source seat has no passenger")
    }
  }

  def bestSeat(rowClass: RowClass, position: SeatPosition, person: Person): Either[String, Booking] = {
    val available = seating.filter { row =>
      (row.rowClass.name == rowClass.name) && (row.seats(position.name) match {
        case FreeSeat => true
        case _ => false
      })
    }

    if(available.isEmpty) {
      Left("No seats available in selected class and position")
    } else {
//      println(seating)
      book(available.head.row, position, person)

    }
  }

  override def toString = {
    seating.foldLeft("") { case (acc, row) =>
      val rowString = row.seats.foldLeft(acc + row.row) { case (o, (_, s)) =>
        o + (s match {
          case FreeSeat => " F "
          case OccupiedSeat(_) => " O "
        })
      }
      rowString + "\n"
    }
  }

}
