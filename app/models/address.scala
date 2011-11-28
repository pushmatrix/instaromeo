package models
 
import java.util.{Date}
 
import play.db.anorm._
import play.db.anorm.SqlParser._
import play.db.anorm.defaults._
import play.data.validation.Annotations._

/*

A domain model for storing address objects. 

The max and min sizes of the model's attributes are derived from the Flower One API.

This is done so that a valid object in our domain can be used to provide valid inputs to the API

*/

case class Address(
    id: Pk[Long], 
    @Required @MaxSize(30) address1: String, 
    @MaxSize(30) address2: Option[String],
    @Required @MaxSize(30) city: String,
    @Required @MaxSize(2) @MinSize(2) state: String,
    @Required @MaxSize(2) @MinSize(2) country: String,
    @Required @MaxSize(6) @MinSize(5) zip: String
)

object Address extends Magic[Address]