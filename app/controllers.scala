package controllers

import play._
import play.mvc._
import models._
import play.data.validation._


object Application extends Controller {
    
    import views.Application._
    
    def index = {
        val orderParams = FlowerOrder("lalaEmail","ewrwe",1,"werwer","e")
        Validation.valid("flowerOrder", orderParams)
        if(validation.hasErrors) {
          Logger.info(validation.errorsMap().toString())
        } else {
          val order = FlowerOrder.create(orderParams)
          println("saving the order")
        }
        girlfriend.html.index("instaRomeo | Just Add Us")
    }
    
}
