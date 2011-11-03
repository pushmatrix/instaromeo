import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
 
public class HomePageTests extends FunctionalTest {
 
    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertContentMatch("instaRomeo | Just Add Us",response);
        assertContentMatch("Enter your Girlfriend's name",response);
        assertContentMatch("When's your next anniversary?",response);
        assertContentMatch("When's her next birthday?",response);
        assertContentMatch("Want some brownie points?",response);
        assertContentMatch("Your order",response);
        assertContentMatch("Delivery Details",response);
        assertContentMatch("Checkout details",response);
    }
    
    @Test
       public void testThat404PageWorks() {
           Response response = GET("/idontexist");
           assertIsOk(response);
           assertContentType("text/html", response);
           assertContentMatch("<title>404</title", response);
           assertContentMatch("tb_sign1.png",response);
       }
    
}