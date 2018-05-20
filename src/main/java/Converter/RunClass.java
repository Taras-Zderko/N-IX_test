package Converter;

import Objects.Address;
import Objects.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;


public class RunClass {
    public static void main(String[] args) {
        User user = new User("Test Name", "Test LastName", new Address("Lviv", "Test Street"));
        convertToJSON(user);
        convertToPropertyes(user);
    }

    private static void convertToJSON(User user) {
        ObjectMapper mapper = new ObjectMapper();
        try (PrintWriter out = new PrintWriter("jsonValueStr.json")) {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            out.write(jsonString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void convertToPropertyes(User user){
        ObjectMapper mapper = new ObjectMapper();
        try (PrintWriter out = new PrintWriter("Properties.properties")){
            Map<String, Object> MyMap = mapper.readValue(new File("jsonValueStr.json"),new TypeReference<Map<String, Object>>() {});
            MyMap.remove("address");
            MyMap.put("address.city", user.getAddress().getCity());
            MyMap.put("address.street", user.getAddress().getStreet());
            Properties properties = new Properties();
            properties.putAll(MyMap);
            out.write(properties.toString()
                    .replace("{", "")
                    .replace("}", "")
                    .replace(",", "\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
