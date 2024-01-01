package SerializationAndDeserialization;

import POJO.PojoLoginClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static api.Route.BASE_PATH;
import static api.Route.BASE_URI;


public class serialAndDeserial {

    @Test
    public void CreateJsonobj() throws JsonProcessingException {

        PojoLoginClass pc = new PojoLoginClass();
        pc.setlogin("akash.j@atc.xyz");
        pc.setpassword("Akash@8897430788");

        //convert class object to json payload as string
        ObjectMapper Classobj = new ObjectMapper();
        String Jsonobj = Classobj.writerWithDefaultPrettyPrinter().writeValueAsString(pc);
        System.out.println(Jsonobj);

        //Using JSONObject class (pre-defined classs) instead of using POJO class
        /*
        JSONObject Jsonobj = new JSONObject();
        Jsonobj.put("login","akash.j@atc.xyz");
        Jsonobj.put("password","Akash@8897430788");
         */

        //create Request Specification
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(Jsonobj)
                .when().post(BASE_PATH)
                .then().statusCode(200)
                .log().all();

        //perform post request
        Response response = requestSpecification.get(BASE_URI);

        //Validate status code
        Assert.assertEquals("Response status code is not 200", 200, response.getStatusCode());
        System.out.println("Assertion passed: Response status code is 200");

        //convert json string to class object
        PojoLoginClass pc1= Classobj.readValue(Jsonobj, PojoLoginClass.class);
    }
}
