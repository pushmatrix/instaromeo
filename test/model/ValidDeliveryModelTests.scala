import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import play.data.validation._
import models._    
import play.db.anorm._
import java.util.{Date}
import java.sql.Timestamp
import java.text.SimpleDateFormat

class ValidDeliveryModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
    val format = new SimpleDateFormat("MM/dd/yy")
    
    override def beforeEach() {
        Fixtures.deleteDatabase() //delete all test data between tests
        Validation.clear() //clear all validation errors between tests
        Yaml[List[Any]]("address.yml").foreach { 
            _ match {
                case a:Address => Address.create(a)
            }
        }
        Yaml[List[Any]]("pricescheme.yml").foreach { 
            _ match {
                case p:PriceScheme => PriceScheme.create(p)
            }
        }
        Yaml[List[Any]]("flowerorder.yml").foreach { 
            _ match {
                case o:FlowerOrder => FlowerOrder.create(o)
            }
        }
    }
    
    it should "Create and retrieve a valid delivery with card note and special instructions" in {
        var date = new Date();
        date.setYear(date.getYear()+1)
        var timestamp = new Timestamp(date.getTime())
       
        var testDelivery = Delivery(Id(1),
            1,
            Option("some cheesey line"),
            Option("dont make me sound cheesey"),
            timestamp,
            1,
            false,
            0.0
        )
        
        Validation.valid("Delivery", testDelivery)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (false)

        Delivery.create(testDelivery)

        val delivery = Delivery.find("id={id}").on("id" -> 1).first()
        (delivery) should not be (None)
        (delivery.get.flowerOrder) should be (1)
        (delivery.get.cardNote.get) should be ("some cheesey line")
        (delivery.get.specialInstructions.get) should be ("dont make me sound cheesey")
        (format.format(delivery.get.deliveryDate)) should be (format.format(timestamp))
        (delivery.get.priceScheme) should be (1)
        (delivery.get.orderPlaced) should be (false)
        (delivery.get.amountSpent) should be (0.0)
    }
    
    it should "Create and retrieve a valid delivery without card note and special instructions" in {
        var date = new Date();
        date.setYear(date.getYear()+1)
        var timestamp = new Timestamp(date.getTime())
        
        var testDelivery = Delivery(Id(1),
            1,
            null,
            null,
            timestamp,
            1,
            false,
            0.0
        )
        
        Validation.valid("Delivery", testDelivery)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (false)

        Delivery.create(testDelivery)

        val delivery = Delivery.find("id={id}").on("id" -> 1).first()
        (delivery) should not be (None)
        (delivery.get.flowerOrder) should be (1)
        (delivery.get.cardNote.isEmpty) should be (true)
        (delivery.get.specialInstructions.isEmpty) should be (true)
        (format.format(delivery.get.deliveryDate)) should be (format.format(timestamp))
        (delivery.get.priceScheme) should be (1)
        (delivery.get.orderPlaced) should be (false)
        (delivery.get.amountSpent) should be (0.0)
    }
    
}