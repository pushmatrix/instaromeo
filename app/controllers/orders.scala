package controllers

import play._
import play.mvc._
import models._   
import play.db.anorm._
import play.data.validation._
import scala.util.parsing.json._
import java.text.SimpleDateFormat;

object Orders extends Controller {

    def create() = {
        JSON.parseFull(params.get("deliveries")) match {
          case Some(x) => {
            var deliveries = x.asInstanceOf[List[Map[String, Any]]]
            deliveries foreach( (delivery) => {
              /* get the type of the delivery with delivery("type") */
              var newDelivery = Delivery(NotAssigned,
                         1,
                         Option(""),
                         Option(""),
                         new java.util.Date(), /* use delivery("date") to access the date from json*/
                         1,
                         true,
                         100)

              Validation.valid("Delivery", newDelivery)
              if(validation.hasErrors) {
                  Logger.info(validation.errorsMap().toString())
              } else {
                  newDelivery = Delivery.create(newDelivery).get
                  println("saving delivery")
              }
            })
            
          }
        }
        
        var newAddress = Address(NotAssigned,
                params.get("senderAddress1"),
                Option(params.get("senderAddress2")),
                params.get("senderCity"),
                params.get("senderState"),
                params.get("senderCountry"),
                params.get("senderZip"))


        Validation.valid("Address", newAddress)
        if(validation.hasErrors) {
            Logger.info(validation.errorsMap().toString())
        } else {
            newAddress = Address.create(newAddress).get
            println("saving sender address")
        }

        val newOrder = FlowerOrder(NotAssigned,
                params.get("senderEmail"),
                params.get("senderName"),
                params.get("senderPhone"),
                newAddress.id(),
                params.get("recipientName"),
                params.get("recipientPhone"))

        Validation.valid("FlowerOrder", newOrder)
        if(validation.hasErrors) {
            Logger.info(validation.errorsMap().toString())
        } else {
            FlowerOrder.create(newOrder)
            println("saving the order")
        }
    }
}