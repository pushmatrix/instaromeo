import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import play.data.validation._
import models._    
import play.db.anorm._

class InvalidAddressModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
    override def beforeEach() {
        Fixtures.deleteDatabase() //delete all test data between tests
        Validation.clear() //clear all validation errors between tests
    }
    
    it should "attempt and fail to create a valid address with an address1 that is to long" in {
          var testAddress = Address(Id(1),
                        "123 Test Street 123 Test Street 123 Test Street 123 Test Street",
                        Option("unit 456"),
                        "Testerton",
                        "ON",
                        "CA",
                        "H0H0H0")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with an address2 that is to long" in {
          var testAddress = Address(Id(1),
                        "123 Test Street",
                        Option("unit 456 unit 456 unit 456 unit 456 unit 456"),
                        "Testerton",
                        "ON",
                        "CA",
                        "H0H0H0")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with an city that is to long" in {
          var testAddress = Address(Id(1),
                        "123 Test Street",
                        Option("unit 456"),
                        "Testerton Testerton Testerton Testerton Testerton Testerton",
                        "ON",
                        "CA",
                        "H0H0H0")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with state/prov that is to long" in {
          var testAddress = Address(Id(1),
                        "123 Test Street",
                        Option("unit 456"),
                        "Testerton",
                        "ONN",
                        "CA",
                        "H0H0H0")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with state/prov that is to short" in {
          var testAddress = Address(Id(1),
                        "123 Test Street",
                        Option("unit 456"),
                        "Testerton",
                        "O",
                        "CA",
                        "H0H0H0")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with country that is to long" in {
          var testAddress = Address(Id(1),
                        "123 Test Street",
                        Option("unit 456"),
                        "Testerton",
                        "ON",
                        "CAN",
                        "H0H0H0")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with country that is to short" in {
          var testAddress = Address(Id(1),
                        "123 Test Street",
                        Option("unit 456"),
                        "Testerton",
                        "ON",
                        "CAA",
                        "H0H0H0")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with zip/post that is to long" in {
          var testAddress = Address(Id(1),
                        "123 Test Street",
                        Option("unit 456"),
                        "Testerton",
                        "ON",
                        "CA",
                        "1234567")

            Validation.valid("Address", testAddress)
            val hasErrors =  Validation.hasErrors()
            (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid address with zip/post that is to short" in {
           var testAddress = Address(Id(1),
                         "123 Test Street",
                         Option("unit 456"),
                         "Testerton",
                         "ON",
                         "CA",
                         "1234")

             Validation.valid("Address", testAddress)
             val hasErrors =  Validation.hasErrors()
             (hasErrors) should be (true)
     }
}