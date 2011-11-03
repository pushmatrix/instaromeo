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

class InvalidDeliveryModelTests extends UnitFlatSpec with ShouldMatchers with BeforeAndAfterEach{
    
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
    
    it should "attempt and fail to create a valid delivery with non existant order" in {
        
        var date = new Date();
        date.setYear(date.getYear()+1)//set date to a year from now
        var timestamp = new Timestamp(date.getTime()); //Postgres compatibl data type
       
        var testDelivery = Delivery(Id(1),
            5,
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
        (delivery) should be (None)
    }
    
    it should "attempt and fail to create a valid delivery with card note that is to long" in {
        
        var date = new Date();
        date.setYear(date.getYear()+1)//set date to a year from now
        var timestamp = new Timestamp(date.getTime()); //Postgres compatibl data type
       
        var testDelivery = Delivery(Id(1),
            1,
            Option("some cheesey line some cheesey line some cheesey line some cheesey line some cheesey line some cheesey linesome cheesey line some cheesey line some cheesey line some cheesey line some cheesey line some cheesey line"),
            Option("dont make me sound cheesey"),
            timestamp,
            1,
            false,
            0.0
        )
        
        Validation.valid("Delivery", testDelivery)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid delivery with special instructions that are to long" in {
        
        var date = new Date();
        date.setYear(date.getYear()+1)//set date to a year from now
        var timestamp = new Timestamp(date.getTime()); //Postgres compatibl data type
       
        var testDelivery = Delivery(Id(1),
            1,
            Option("some cheesey line"),
            Option("dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey dont make me sound cheesey"),
            timestamp,
            1,
            false,
            0.0
        )
        
        Validation.valid("Delivery", testDelivery)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid delivery with delivery date in the past" in {
        
        var date = new Date();
        date.setYear(date.getYear()-1)//set date to a year from now
        var timestamp = new Timestamp(date.getTime()); //Postgres compatibl data type
       
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
        (hasErrors) should be (true)
    }
    
    it should "attempt and fail to create a valid delivery with price scheme that doesnt exist" in {
        
        var date = new Date();
        date.setYear(date.getYear()+1)//set date to a year from now
        var timestamp = new Timestamp(date.getTime()); //Postgres compatibl data type
       
        var testDelivery = Delivery(Id(1),
            1,
            Option("some cheesey line"),
            Option("dont make me sound cheesey"),
            timestamp,
            5,
            false,
            0.0
        )
        
        Validation.valid("Delivery", testDelivery)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (false)
        
        Delivery.create(testDelivery)

        val delivery = Delivery.find("id={id}").on("id" -> 1).first()
        (delivery) should be (None)
    }
    
    it should "attempt and fail to create a valid delivery with null required field" in {
        
        var date = new Date();
        date.setYear(date.getYear()+1)//set date to a year from now
        var timestamp = new Timestamp(date.getTime()); //Postgres compatibl data type
       
        var testDelivery = Delivery(Id(1),
            1,
            Option("some cheesey line"),
            Option("dont make me sound cheesey"),
            null,
            1,
            false,
            0.0
        )
        
        Validation.valid("Delivery", testDelivery)
        val hasErrors =  Validation.hasErrors()
        (hasErrors) should be (true)
        
    }
    
}