package models
 
import java.util._
 
import play.db.jpa._
import play.db.jpa.Annotations._
import play.data.validation.Annotations._

@Entity
class Delivery(

    @Email
    @Required
    var email: String,
    
    @Required
    var fullname: String

) extends Model {
    var isAdmin = false
    
    override def toString() = email  
 
}