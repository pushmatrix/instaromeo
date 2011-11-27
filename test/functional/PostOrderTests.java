import org.junit.*;
import play.test.*;
//import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import java.util.Map;
import java.util.HashMap;
 
public class PostOrderTests extends FunctionalTest {
 
    @Test
    public void testCreatingAValidOrder() {
        Map<String, String> params = new HashMap<String,String>();
        params.put("senderAddress1", "123 some street");
        params.put("senderAddress2", "unit 456");
        params.put("senderCity", "Fakes Ville");
        params.put("senderState", "ON");
        params.put("senderCountry","CA");
        params.put("senderZip", "LKFFKL" );
        
        params.put("senderEmail","peanut@butter.com" );
        params.put("senderName", "Jim Jimmers");
        params.put("senderPhone", "1234567890");
        params.put("recipientName", "Your Mom");
        params.put("recipientPhone", "0987654321");
        
        params.put("deliveries", "[{\"type\":\"valentines\",\"date\":\"2012-02-14T05:00:00.000Z\"}]");
        
        Response response = POST("/orders", params);
        assertIsOk(response);
        assertContentType("text/html", response);
        System.out.println(getContent(response));
    }
    
}