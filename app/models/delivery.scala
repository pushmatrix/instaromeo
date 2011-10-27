package models
 
import java.util.{Date}
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

case class Delivery(
    @Required order: Integer,
    @MaxSize(200) cardNote: String,
    @MaxSize(200) specialInstructions: String,
    @Required @InFuture deliveryDate: Date,
    @Required priceScheme: Integer,
    @Required orderPlaced: Boolean,
    @Required amountSpent: Double   
)

object Delivery extends Magic[Delivery]