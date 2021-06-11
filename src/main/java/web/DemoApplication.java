package web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import web.model.User;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://91.241.64.178:7081/api/users", String.class);
        String cookie = forEntity.getHeaders().getFirst("Set-Cookie");

        System.out.println("cookie: "+cookie);


        //ALLUSERS
        headers.set("Cookie", cookie);

        String allusers = restTemplate.exchange("http://91.241.64.178:7081/api/users",
                HttpMethod.GET, forEntity, String.class).getBody();

        System.out.println("allusers: "+allusers);


        //ADDUSER
        headers.set("Cookie", cookie);

        User user1 = new User(3L, "James", "Brown", (byte)22);

        HttpEntity<User> request = new HttpEntity<>(user1, headers);
        String adduser = restTemplate.postForObject("http://91.241.64.178:7081/api/users", request, String.class);

        System.out.println("Code1: " + adduser);


        //PUTUSER
        headers.set("Cookie", cookie);

        User user2 = new User(3L, "Thomas", "Shelby", (byte)22);

        HttpEntity<User> request2 = new HttpEntity<>(user2, headers);
        String putuser = restTemplate.exchange("http://91.241.64.178:7081/api/users",
                HttpMethod.PUT, request2, String.class).getBody();

        System.out.println("Code2: " + putuser);


        //DELETEUSER
        headers.set("Cookie", cookie);

        Map<String, Long> params = new HashMap<>();
        params.put("id", 3L);

        HttpEntity<User> request3 = new HttpEntity<>(headers);
        String deleteuser = restTemplate.exchange("http://91.241.64.178:7081/api/users/3",
                HttpMethod.DELETE, request3, String.class, params).getBody();

        System.out.println("Code3: " + deleteuser);

        System.out.println(adduser + putuser + deleteuser);
    }
}
