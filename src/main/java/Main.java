import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class Main {
    public static void main(String[] args){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String key = response.getHeaders().get("Set-Cookie")
                .get(0).substring(0, response.getHeaders()
                        .get("Set-Cookie").get(0).indexOf(';'));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Cookie",key);

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte)21);
        HttpEntity<User>httpEntity = new HttpEntity<>(user, httpHeaders);
        String response1 = restTemplate.postForObject(url,httpEntity,String.class);

        user.setId(3L);
        user.setName("Tomas");
        user.setLastName("Shelby");
        HttpEntity<User>httpEntity1 = new HttpEntity<>(user,httpHeaders);
        String response2 = restTemplate.exchange(url, HttpMethod.PUT,httpEntity,String.class).getBody();

        HttpEntity<User>httpEntity2 = new HttpEntity<>(httpHeaders);
        String response3 = restTemplate.exchange(url+"/3",HttpMethod.DELETE,httpEntity2,String.class).getBody();

        System.out.println(response1+response2+response3);
    }
}

///Вариант с разбитием ключа по действиям
//private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
//private static String sessionId;
//
//public static void main(String[] args) {
//    RestTemplate restTemplate = new RestTemplate();
//
//    // 1. Получить список всех пользователей
//    getAllUsers(restTemplate);
//
//    // 2. Сохранить пользователя
//    User user = new User();
//    user.setId(3L);
//    user.setName("James");
//    user.setLastName("Brown");
//    user.setAge((byte) 25);
//    saveUser(restTemplate, user);
//
//    // 3. Изменить пользователя
//    user.setName("Thomas");
//    user.setLastName("Shelby");
//    updateUser(restTemplate, user);
//
//    // 4. Удалить пользователя
//    deleteUser(restTemplate, user.getId());
//}
//
//private static void getAllUsers(RestTemplate restTemplate) {
//    ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL, User[].class);
//    List<User> users = Arrays.asList(response.getBody());
//
//    // Сохранение session id из заголовка Set-Cookie
//    sessionId = response.getHeaders().get("Set-Cookie").get(0);
//    System.out.println("All Users: " + users);
//    System.out.println("Session ID: " + sessionId);
//}
//
//private static void saveUser(RestTemplate restTemplate, User user) {
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//    headers.add("Cookie", sessionId);
//
//    HttpEntity<User> request = new HttpEntity<>(user, headers);
//    ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, request, String.class);
//
//    System.out.println("Save User Response: " + response.getBody());
//}
//
//private static void updateUser(RestTemplate restTemplate, User user) {
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//    headers.add("Cookie", sessionId);
//
//    HttpEntity<User> request = new HttpEntity<>(user, headers);
//    ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, String.class);
//
//    System.out.println("Update User Response: " + response.getBody());
//}
//
//private static void deleteUser(RestTemplate restTemplate, Long userId) {
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Cookie", sessionId);
//
//    HttpEntity<String> request = new HttpEntity<>(headers);
//    ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/" + userId, HttpMethod.DELETE, request, String.class);
//
//    System.out.println("Delete User Response: " + response.getBody());
//}
//}

