import play.jobs._
import play.data.validation._

/*

    Used to bootstrap the application with seed data. 
    
    It uses the data stored in /conf/initial-data.yml 
    
    Currently, it only loads Price Schemes.
    
    If the application is running in Dev or Test mode, this data will be loaded on the first HTTP request
    
    in Prod mode this will happen when the server starts. 

*/
    
@OnApplicationStart class BootStrap extends Job {
    
    override def doJob {
        
        import models._
        import play.test._
        
        if(PriceScheme.count().single() == 0) {
            Yaml[List[Any]]("initial-data.yml").foreach { 
                _ match {
                    case a:Address => Address.create(a)
                    case p:PriceScheme => PriceScheme.create(p)
                    case o:FlowerOrder => FlowerOrder.create(o)
                }
            }
        }
        
    }
    
}