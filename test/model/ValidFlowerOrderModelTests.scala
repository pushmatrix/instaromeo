import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import play.data.validation._
import models._    
import play.db.anorm._

class ValidFlowerOrderModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
    override def beforeEach() {
        Fixtures.deleteDatabase() //delete all test data between tests
        Validation.clear() //clear all validation errors between tests
    }
    
    it should "Create and retrieve a valid flower order" in {
        var testAddress = Address(Id(1),
                    "123 Test Street",
                    Option("unit 456"),
                    "Testerton",
                    "ON",
                    "CA",
                    "H0H0H0")

        Address.create(testAddress)
        
        var testOrder = FlowerOrder(Id(1),
            "test@test.com",
            "Peanutbutter",
            "1234567890",
            1, //need foreign key validation
            "Jelly",
            "0987654321" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (false)

        FlowerOrder.create(testOrder)

        val order = FlowerOrder.find("id={id}").on("id" -> 1).first()
        (order) should not be (None)
        (order.get.senderEmail) should be ("test@test.com")
        (order.get.senderName) should be ("Peanutbutter")
        (order.get.senderPhone) should be ("1234567890")
        (order.get.senderAddress) should be (1)
        (order.get.recipientName) should be ("Jelly")
        (order.get.recipientPhone) should be ("0987654321")
    }
}