package com.mehmetyucel.bitrise

import com.mehmetyucel.bitrise.dependency.Dependencies
import org.scalatest.mockito.MockitoSugar
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{Matchers, PrivateMethodTester, WordSpec}

import scala.io.Source

trait BaseTest extends WordSpec
  with Matchers
  with TableDrivenPropertyChecks
  with PrivateMethodTester
  with MockitoSugar {

  val df = new Dependencies()


}