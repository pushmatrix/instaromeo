import play.jobs._
import play.data.validation._
    
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