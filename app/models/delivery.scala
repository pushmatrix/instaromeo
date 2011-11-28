package models
 
import java.util.{Date}
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

/*

A domain model for storing delivery objects.

A delivery refers to a single item of a given price grade which is to be delivered to an indiviudal. 

A delivery belongs to a flower order and has an address which represents the address of the eprson to whom the item is to be delivered to.  

The max and min sizes of the model's attributes are derived from the Flower One API.

This is done so that a valid object in our domain can be used to provide valid inputs to the API

*/

case class Delivery(
    id: Pk[Long], 
    @Required flowerOrder: Long,
    @MaxSize(200) cardNote: Option[String],
    @MaxSize(200) specialInstructions: Option[String],
    @Required @InFuture deliveryDate: Date, //when created, the date of delivery must be in the future relative to the current date. We do not posses the ability of time travel
    @Required priceScheme: Long,
    @Required orderPlaced: Boolean,
    @Required amountSpent: Double,   
    @Required deliveryAddress: Long
)

object Delivery extends Magic[Delivery]