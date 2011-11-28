package models
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

/*

A domain model for storing flower order objects. 

A flower order represents an order placed with instaromeo. 

A flower order can have 1 - many deliveries. 

It stores the senders infomration including an address object

The max and min sizes of the model's attributes are derived from the Flower One API.

This is done so that a valid object in our domain can be used to provide valid inputs to the API

*/

case class FlowerOrder(
    id: Pk[Long], 
    @Required @Email @MaxSize(100) senderEmail: String,
    @Required @MaxSize(50) senderName: String,
    @Required @MaxSize(10) @MinSize(10) senderPhone: String,
    @Required senderAddress: Long,
    @Required @MaxSize(50) recipientName: String,
    @Required @MaxSize(10) @MinSize(10) recipientPhone: String
)

object FlowerOrder extends Magic[FlowerOrder]