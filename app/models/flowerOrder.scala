package models
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

case class FlowerOrder(
    @Email senderEmail: String,
    senderName: String,
    senderAddress: Integer,
    recipientName: String,
    @Required recipientPhone: String
)

object FlowerOrder extends Magic[FlowerOrder]