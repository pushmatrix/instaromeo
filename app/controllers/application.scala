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
    
    def http404 = {
        errors.html.http404()
    }
}
