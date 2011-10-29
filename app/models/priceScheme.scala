package models
 
import java.util.{Date}
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

case class PriceScheme(
    id: Pk[Long], 
    @Required name: String,
    @Required chargePrice: Double, 
    @Required maxSpend: Double,
    @Required minSpend: Double
)

object PriceScheme extends Magic[PriceScheme]