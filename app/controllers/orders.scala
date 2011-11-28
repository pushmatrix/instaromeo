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

//orders controller

object Orders extends Controller {

    //Create Flower Order Action
    def create() = {
        var errors = ""
        //Create an address for the sender
        var senderAddress = Address(NotAssigned,
                params.get("senderAddress1"),
                Option(params.get("senderAddress2")),
                params.get("senderCity"),
                params.get("senderState"),
                params.get("senderCountry"),
                params.get("senderZip"))

        //validate the senders address. Only persist the address if its valid
        Validation.valid("Address", senderAddress)
        if(validation.hasErrors) {
            errors += validation.errorsMap().toString();
        } else {
            senderAddress = Address.create(senderAddress).get //persist
            println("saving sender address")
        }
        
         //Create an address for the receiver
        var recipientAddress = Address(NotAssigned,
                params.get("recipientAddress1"),
                Option(params.get("recipientAddress2")),
                params.get("recipientCity"),
                params.get("recipientState"),
                params.get("recipientCountry"),
                params.get("recipientZip"))

        //validate the receivers address. Only persist the address if its valid
        Validation.valid("Address", recipientAddress)
        if(validation.hasErrors) {
            errors += validation.errorsMap().toString();
        } else {
            recipientAddress = Address.create(recipientAddress).get //persist
            println("saving recipient address")
        }
        
        //create a new flower order using the sender's address
        var newOrder = FlowerOrder(NotAssigned,
                params.get("senderEmail"),
                params.get("senderName"),
                params.get("senderPhone"),
                senderAddress.id(),
                params.get("recipientName"),
                params.get("recipientPhone"))

        //validate the flower order, persist only if valid
        Validation.valid("FlowerOrder", newOrder)
        if(validation.hasErrors) {
            errors += validation.errorsMap().toString();
        } else {
            newOrder = FlowerOrder.create(newOrder).get //persist
            println("saving the order")
        }
        
        //parse the json object containing the collection of deliveries. Create a new delivery for each delivery
        JSON.parseFull(params.get("deliveries")) match {
          case Some(x) => {
            var deliveries = x.asInstanceOf[List[Map[String, Any]]]
            System.out.println("Deliveries: " + deliveries)    
            deliveries foreach( (delivery) => {
              /* get the type of the delivery with delivery("type") */
              
              var priceScheme = PriceScheme.find("name={name}").on("name" -> delivery("type")).first()
              
              var strDate = delivery("date").toString().substring(0,10)
              var deliveryDate = new SimpleDateFormat("yyyy-MM-dd").parse(strDate)
              deliveryDate = new Timestamp(deliveryDate.getTime())
              System.out.println(priceScheme)
              //create a new delivery
              var newDelivery = Delivery(NotAssigned,
                         newOrder.id(),
                         Option(""),
                         Option(""),
                         deliveryDate, /* use delivery("date") to access the date from json*/
                         priceScheme.get.id(),
                         false,
                         0.0,
                         recipientAddress.id())
              //validate delivery, persist only if valid
              Validation.valid("Delivery", newDelivery)
              if(validation.hasErrors) {
                  errors += validation.errorsMap().toString();
              } else {
                  newDelivery = Delivery.create(newDelivery).get //persist
                  println("saving delivery")
              }
            })
            errors
          }
        }
    }
}