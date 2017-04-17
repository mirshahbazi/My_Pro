package top.linjia.wizarposapp;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;

public class GsonTest {

    @org.junit.Test
    public void test(){
        ArrayList<Object> cartIds = new ArrayList<>();
        cartIds .add(11111);
        cartIds .add(2222);
        JsonObject json = new JsonObject();
        JsonArray cartIdsStr = new  JsonArray();
        for(Object cartId : cartIds){
            cartIdsStr.add(cartId+"");
        }
        json.addProperty("appToken","aaaa");//填充appToken
        json.add("cartIds",cartIdsStr);
        System.out.print(json.toString());
    }

}
