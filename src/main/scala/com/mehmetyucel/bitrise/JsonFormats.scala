package com.mehmetyucel.bitrise

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import com.mehmetyucel.bitrise.data._
import com.mehmetyucel.bitrise.request._
//import spray.json.{DefaultJsonProtocol, JsNull, JsObject, JsString, JsValue, JsonWriter, RootJsonFormat}
import spray.json._

import scala.reflect.ClassTag


trait JsonFormats extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val booleanMarshaller: ToEntityMarshaller[Boolean] =
    Marshaller.opaque {
      case true => "true"
      case false => "false"
    }

  implicit val t1  = jsonFormat2( BookingSuccess )
  implicit val t2  = jsonFormat2( BookingError )
  implicit val t3  = jsonFormat4( Person )
  implicit val t4  = jsonFormat1( OccupiedSeat )
  implicit val t5  = jsonFormat3( BookRequest )
  implicit val t6  = jsonFormat2( UnBookingRequest )
  implicit val t7  = jsonFormat4( AmendRequest )
  implicit val t8  = jsonFormat3( BookBestRequest )
  implicit val t9  = jsonFormat4( Booking )

  implicit object classJsonFormat extends RootJsonFormat[RowClass] {
    def write(a: RowClass) = a match {
      case Business => JsString("Business")
      case Economy => JsString("Economy")
    }
    def read(value: JsValue) = {
      value.asJsObject.fields("rowClass") match {
        case JsString("Business") => Business
        case JsString("Economy") => Economy
      }
    }
  }

  implicit object positionJsonFormat extends RootJsonFormat[SeatPosition] {
    def write(a: SeatPosition) = a match {
      case Window => JsString("Window")
      case Center => JsString("Center")
      case Aisle => JsString("Aisle")
    }
    def read(value: JsValue) = {
      value.asJsObject.fields("rowClass") match {
        case JsString("Window") => Window
        case JsString("Aisle") => Center
        case JsString("Center") => Aisle
      }
    }
  }

  implicit object seatJsonFormat extends RootJsonFormat[Seat] {
    def write(a: Seat) = a match {
      case o: OccupiedSeat => o.toJson // JsString("boom")//write(o)
      case FreeSeat => JsObject("passenger" -> JsString("None"))
    }
    def read(value: JsValue) = {
      value.asJsObject.fields("person") match {
        case JsString("person") => value.convertTo[OccupiedSeat]
        case JsNull => FreeSeat
      }
    }}
  implicit val t10  = jsonFormat3( Row )

  def jsonObjectFormat[ A: ClassTag ]: RootJsonFormat[ A ] = new RootJsonFormat[ A ] {
    val ct = implicitly[ ClassTag[ A ] ]

    def write( obj: A ): JsValue = JsObject( "value" -> JsString( ct.runtimeClass.getSimpleName ) )

    def read( json: JsValue ): A = ct.runtimeClass.newInstance( ).asInstanceOf[ A ]
  }

}