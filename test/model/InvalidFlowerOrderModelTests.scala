import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import play.data.validation._
import models._    
import play.db.anorm._

class InvalidFlowerOrderModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
    override def beforeEach() {
        Fixtures.deleteDatabase() //delete all test data between tests
        Validation.clear() //clear all validation errors between tests
    }
    
    it should "attempt and fail create a valid flower order with a malformed email" in {
        var testOrder = FlowerOrder(Id(1),
            "test@testcom",
            "Peanutbutter",
            "1234567890",
            1, //need foreign key validation
            "Jelly",
            "0987654321" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail create a valid flower order with a sender name that is to long" in {
        var testOrder = FlowerOrder(Id(1),
            "test@test.com",
            "Peanutbutterereanutbuttereanutbuttereanutbuttereanutbuttereanutbutter",
            "1234567890",
            1, //need foreign key validation
            "Jelly",
            "0987654321" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail create a valid flower order with a sender phone number that is to long" in {
        var testOrder = FlowerOrder(Id(1),
            "test@test.com",
            "Peanutbutter",
            "1234567890123",
            1, //need foreign key validation
            "Jelly",
            "0987654321" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail create a valid flower order with a sender phone number that is to short" in {
        var testOrder = FlowerOrder(Id(1),
            "test@test.com",
            "Peanutbutter",
            "12345678",
            1, //need foreign key validation
            "Jelly",
            "0987654321" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create and retrieve a valid flower order with none existent address id" in {    
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
        (order) should be (None)
    }
    
    it should "attempt and fail create a valid flower order with a recipient name that is to long" in {
        var testOrder = FlowerOrder(Id(1),
            "test@test.com",
            "Peanutbutter",
            "1234567890",
            1, //need foreign key validation
            "JellyJellyJellyJellyJellyJellyJellyJellyJellyJelly!",
            "0987654321" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail create a valid flower order with a recipient phone number that is to long" in {
        var testOrder = FlowerOrder(Id(1),
            "test@test.com",
            "Peanutbutter",
            "1234567890",
            1, //need foreign key validation
            "Jelly",
            "09876543219" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail create a valid flower order with a recipient phone number that is to short" in {
        var testOrder = FlowerOrder(Id(1),
            "test@test.com",
            "Peanutbutter",
            "1234567890",
            1, //need foreign key validation
            "Jelly",
            "098765432" 
        )
        
        Validation.valid("FlowerOrder", testOrder)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
}