package models
 
import java.util.{Date}
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

/*

A domain model for storing price scheme objects. 

Each delivery is associated with a price scheme which dictates how much we charge for the item and how much of that we spend using Flower One. 

The chargePrice, represents what we charge the customer for that type of item. 

We then spend somewhere between minSpend and maxSpend when ordering the item from flower one. 

For example: if for valenties day items, we charge $120 and have a minSpend of $80 and a maxSpend of $100

we will make anywhere from $20 - $40 profit and the receiver will receive an item valued at $80 - $100

The max and min sizes of the model's attributes are derived from the Flower One API.

This is done so that a valid object in our domain can be used to provide valid inputs to the API

*/

case class PriceScheme(
    id: Pk[Long], 
    @Required name: String,
    @Required @Min(1) chargePrice: Double, 
    @Required @Min(1) maxSpend: Double,
    @Required @Min(1) minSpend: Double
)

object PriceScheme extends Magic[PriceScheme]