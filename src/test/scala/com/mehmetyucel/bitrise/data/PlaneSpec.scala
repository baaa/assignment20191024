package com.mehmetyucel.bitrise.data

import com.mehmetyucel.bitrise.{BaseTest, Booking}

class PlaneSpec extends BaseTest {

  val person1 = Person("John Doe", "10/10/1919", "PASS1234", "CN")
  val person2 = Person("Jane Doe", "11/11/1919", "PASS5678", "DE")
  val person3 = Person("Josh Doe", "12/12/1919", "PASS0000", "GB")
  val person4 = Person("Jack Doe", "13/13/1919", "PASS1111", "GR")
  val person5 = Person("Jule Doe", "14/14/1919", "PASS2222", "FR")
  val plane = Plane(3, 5)

  "book" should {

    "allow a free seat to be booked" in {
      val response = plane.book(2, Window, person1)
      response shouldBe Right(Booking(2, Business.name, Window.name, person1))
      plane.seating(1).seats(Window.name) shouldBe OccupiedSeat(person1)
    }

    "not allow same seat to be booked again" in {
      val response = plane.book(2, Window, person2)
      response shouldBe Left("Requested seat already occupied")
      plane.seating(1).seats(Window.name) shouldBe OccupiedSeat(person1)
    }
  }

  "inspect" should {
    "display person information if the seat is booked" in {
      plane.inspect(2, Window) shouldBe OccupiedSeat(person1)
    }

    "show a free seat if the seat is not booked" in {
      plane.inspect(3, Aisle) shouldBe FreeSeat
    }
  }

  "unBook" should {
    "remove passenger if the seat is booked" in {
      plane.unBook(2, Window)
      plane.seating(1).seats(Window.name) shouldBe FreeSeat
    }

    "silently do nothing if the target seat is already empty" in {
      plane.unBook(2, Aisle)
      plane.seating(1).seats(Aisle.name) shouldBe FreeSeat
    }
  }

  "movePassenger" should {
    "should fail to move, if the source seat is empty" in {
      plane.book(2, Window, person1)
      plane.book(3, Window, person2)
      plane.movePassenger(5, Window, 6, Aisle) shouldBe Left("Source seat has no passenger")
    }

    "should fail to move, if the target seat is not empty" in {
      plane.movePassenger(2, Window, 3, Window) shouldBe Left("Requested seat already occupied")
    }

    "should move an exisiting passenger to requested free seat" in {
      plane.movePassenger(2, Window, 4, Aisle) shouldBe Right(Booking(4, Economy.name, Aisle.name, person1))
    }
  }

  "bestSeat" should {
    "assign the best seat, given a class and position" in {
      plane.bestSeat(Business, Window, person3) shouldBe Right(Booking(1, Business.name, Window.name, person3))
      plane.bestSeat(Business, Window, person4) shouldBe Right(Booking(2, Business.name, Window.name, person4))
    }

    "fail to book if requested seat and class has nothing available" in {
      plane.bestSeat(Business, Window, person5) shouldBe Left("No seats available in selected class and position")
//      println(plane)
    }
  }
}
