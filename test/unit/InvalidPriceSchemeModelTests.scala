import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import play.data.validation._
import models._    
import play.db.anorm._

class InvalidPriceSchemeModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
    override def beforeEach() {
        Fixtures.deleteDatabase() //delete all test data between tests
        Validation.clear() //clear all validation errors between tests
    }
    
    it should "attempt and fail to create a valid price scheme with chagePrice less then $1" in {
        var testPriceScheme = PriceScheme (Id(1),
            "Test Scheme",
            0.95,
            60.00,
            40.00 
        )
        
        Validation.valid("PriceScheme", testPriceScheme)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)

        PriceScheme.create(testPriceScheme)
    }
    
    it should "attempt and fail to create a valid price scheme with maxSpend less then $1" in {
        var testPriceScheme = PriceScheme (Id(1),
            "Test Scheme",
            65.95,
            0.10,
            40.00 
        )
        
        Validation.valid("PriceScheme", testPriceScheme)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)

        PriceScheme.create(testPriceScheme)
    }
    
    it should "attempt and fail to create a valid price scheme with minSpend less then $1" in {
        var testPriceScheme = PriceScheme (Id(1),
            "Test Scheme",
            65.95,
            60.00,
            0.50 
        )
        
        Validation.valid("PriceScheme", testPriceScheme)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)

        PriceScheme.create(testPriceScheme)
    }
    
    it should "attempt and fail to create a valid price scheme with a null value" in {
        var testPriceScheme = PriceScheme (Id(1),
            null,
            65.95,
            60.00,
            40.00 
        )
        
        Validation.valid("PriceScheme", testPriceScheme)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)

        PriceScheme.create(testPriceScheme)
    }
}