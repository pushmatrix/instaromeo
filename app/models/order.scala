package models
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._

case class Order(
    email: String,
    senderName: String,
    receiverName: String,
)