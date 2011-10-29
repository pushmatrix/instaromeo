package controllers

import play._
import play.mvc._
import models._   
import play.db.anorm._
import play.data.validation._


object Application extends Controller {
    
    import views.Application._
    
    def index = {
        girlfriend.html.index("instaRomeo | Just Add Us")
    }
    
    def createOrder() = {
    
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
            Address.create(newAddress)
            println("saving sender address")
        }
        
        val newOrder = FlowerOrder(NotAssigned,
                params.get("senderEmail"),
                params.get("senderName"),
                params.get("senderPhone"),
                newAddress.id.apply,
                params.get("recepientName"),
                params.get("recepientPhone"))
                
        Validation.valid("FlowerOrder", newOrder)
        if(validation.hasErrors) {
            Logger.info(validation.errorsMap().toString())
        } else {
            FlowerOrder.create(newOrder)
            println("saving the order")
        }
    }
}
