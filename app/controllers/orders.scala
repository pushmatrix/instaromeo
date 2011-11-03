package controllers

import play._
import play.mvc._
import models._
import views.Application._   
import play.db.anorm._
import play.data.validation._
import scala.util.parsing.json._
import java.text.SimpleDateFormat;
import java.sql.Timestamp
import java.util.{Date}

object Orders extends Controller {

    def create() = {
        
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

        var newOrder = FlowerOrder(NotAssigned,
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
            newOrder = FlowerOrder.create(newOrder).get
            println("saving the order")
        }
        
        JSON.parseFull(params.get("deliveries")) match {
          case Some(x) => {
            var deliveries = x.asInstanceOf[List[Map[String, Any]]]
            System.out.println("Deliveries: " + deliveries)    
            deliveries foreach( (delivery) => {
              /* get the type of the delivery with delivery("type") */
             
             
                
              var priceScheme = PriceScheme.find("name={name}").on("name" -> delivery("type")).first()
              
              var strDate = delivery("date").toString().substring(0,10)
              var deliveryDate = new SimpleDateFormat("yyyy-MM-dd").parse(strDate)
              deliveryDate.setYear(deliveryDate.getYear())
              deliveryDate = new Timestamp(deliveryDate.getTime())
              System.out.println(priceScheme)
              var newDelivery = Delivery(NotAssigned,
                         newOrder.id(),
                         Option(""),
                         Option(""),
                         deliveryDate, /* use delivery("date") to access the date from json*/
                         priceScheme.get.id(),
                         false,
                         0.0)
              System.out.println("wefwe")  
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
        girlfriend.html.index("instaRomeo | Just Add Us")
    }
}