import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import play.data.validation._
import models._    
import play.db.anorm._

class ValidAddressModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
    override def beforeEach() {
        Fixtures.deleteDatabase()
    }
    
    it should "create and retrieve a valid canadian two line Address" in {
        var testAddress = Address(Id(1),
                    "123 Test Street",
                    Option("unit 456"),
                    "Testerton",
                    "ON",
                    "CA",
                    "H0H0H0")
        
        Validation.valid("Address", testAddress)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (false)
        
        Address.create(testAddress)
        
        val address = Address.find("id={id}").on("id" -> 1).first()
        (address) should not be (None)
        (address.get.address1) should be ("123 Test Street")
        (address.get.address2.get) should be ("unit 456")
        (address.get.city) should be ("Testerton")
        (address.get.state) should be ("ON")
        (address.get.country) should be ("CA")
        (address.get.zip) should be ("H0H0H0")
    }
    
    it should "create and retrieve a valid american two line Address" in {
         var testAddress = Address(Id(1),
                     "123 Test Street",
                     Option("unit 456"),
                     "Testerton",
                     "WA",
                     "US",
                     "98109")

         Validation.valid("Address", testAddress)
         val hasErrors =  Validation.hasErrors()
         (hasErrors) should be (false)

         Address.create(testAddress)

         val address = Address.find("id={id}").on("id" -> 1).first()
         (address) should not be (None)
         (address.get.address1) should be ("123 Test Street")
         (address.get.address2.get) should be ("unit 456")
         (address.get.city) should be ("Testerton")
         (address.get.state) should be ("WA")
         (address.get.country) should be ("US")
         (address.get.zip) should be ("98109")
     }
    
    it should "create and retrieve a valid one line Address" in {
           var testAddress = Address(Id(1),
                       "123 Test Street",
                       null,
                       "Testerton",
                       "ON",
                       "CA",
                       "H0H0H0")

           Validation.valid("Address", testAddress)
           val hasErrors =  Validation.hasErrors()
           (hasErrors) should be (false)

           Address.create(testAddress)

           val address = Address.find("id={id}").on("id" -> 1).first()
           (address) should not be (None)
           (address.get.address1) should be ("123 Test Street")
           (address.get.address2.isEmpty) should be (true) //check that the optional value is empty
           (address.get.address2.getOrElse("")) should be ("") //check that when empty, getOrElse returns an empty string
           (address.get.city) should be ("Testerton")
           (address.get.state) should be ("ON")
           (address.get.country) should be ("CA")
           (address.get.zip) should be ("H0H0H0")
       }
}