package models
 
import java.util.{Date}
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

case class PriceScheme(
    @Required @MaxSize(30) address1: String, 
    @MaxSize(30) address2: String,
    @Required @MaxSize(30) city: String,
    @Required @MaxSize(2) @MinSize(2) state: String,
    @Required @MaxSize(2) @MinSize(2) country: String,
    @Required @MaxSize(6) @MinSize(5) zip: String
)

object PriceScheme extends Magic[PriceScheme]