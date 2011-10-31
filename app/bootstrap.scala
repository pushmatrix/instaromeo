import play.jobs._
import play.data.validation._
    
@OnApplicationStart class BootStrap extends Job {
    
    override def doJob {
        
        import models._
        import play.test._
        
        Fixtures.deleteDatabase() //delete all test data between tests
        Fixtures.delete()
        Validation.clear() //clear all validation errors between tests
        Yaml[List[Any]]("initial-data.yml").foreach { 
            _ match {
                case a:Address => Address.create(a)
                case p:PriceScheme => PriceScheme.create(p)
                case o:FlowerOrder => FlowerOrder.create(o)
            }
        }
        
    }
    
}