package models
 
import java.util.{Date}
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

case class Delivery(
    id: Pk[Long], 
    @Required flowerOrder: Long,
    @MaxSize(200) cardNote: Option[String],
    @MaxSize(200) specialInstructions: Option[String],
    @Required @InFuture deliveryDate: Date,
    @Required priceScheme: Long,
    @Required orderPlaced: Boolean,
    @Required amountSpent: Double   
)

object Delivery extends Magic[Delivery]