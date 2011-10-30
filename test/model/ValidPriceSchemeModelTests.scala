import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import play.data.validation._
import models._    
import play.db.anorm._

class ValidPriceSchemeModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
    override def beforeEach() {
        Fixtures.deleteDatabase() //delete all test data between tests
        Validation.clear() //clear all validation errors between tests
    }
    
    it should "Create and retrieve a valid price scheme" in {
        var testPriceScheme = PriceScheme (Id(1),
            "Test Scheme",
            64.95,
            60.00,
            40.00 
        )
        
        Validation.valid("PriceScheme", testPriceScheme)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (false)

        PriceScheme.create(testPriceScheme)

        val priceScheme = PriceScheme.find("id={id}").on("id" -> 1).first()
        (priceScheme) should not be (None)
        (priceScheme.get.name) should be ("Test Scheme")
        (priceScheme.get.chargePrice) should be (64.95)
        (priceScheme.get.maxSpend) should be (60.00)
        (priceScheme.get.minSpend) should be (40.00)
    }
}