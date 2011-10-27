package models
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

case class FlowerOrder(
    @Required @Email @MaxSize(100) senderEmail: String,
    @Required @MaxSize(50) senderName: String,
    @Required @MaxSize(10) senderPhone: String,
    @Required senderAddress: Integer,
    @Required @MaxSize(50) recipientName: String,
    @Required @MaxSize(10) recipientPhone: String
)

object FlowerOrder extends Magic[FlowerOrder]