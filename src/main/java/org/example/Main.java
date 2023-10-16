package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        getAllCommentFromLastPost(1L);
    }

    public static void creatingUser() throws IOException, InterruptedException, URISyntaxException {
        Gson gson = new GsonBuilder().create();
        User userRequest = User.builder()
                .name("Artemchik")
                .username("Artemchik1337")
                .email("artemlaptev2008@gmail.com")
                .address(new Address("Eskova", "Apt. 228", "Chernihiv", "56239-561", new Geo("-56,675", "45,294")))
                .phone("0963842872")
                .website("https://badoo.com/ru/landing")
                .company(new Company("Artem-Enternainment", "Something", "Something2"))
                .build();

        String uri = "https://jsonplaceholder.typicode.com/users";
        HttpRequest createUser = HttpRequest.newBuilder(new URI(uri))
                .method("POST", HttpRequest.BodyPublishers.ofString(gson.toJson(userRequest)))
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json; charset=utf-8")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(createUser, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    public static void updateUser() throws IOException, InterruptedException, URISyntaxException {
        Gson gson = new GsonBuilder().create();
        User userRequest = User.builder()
                .name("Anton")
                .username("Antosha7845")
                .email("antonchik1754@gmail.com")
                .phone("0637460291")
                .build();

        String uri = "https://jsonplaceholder.typicode.com/users/1";
        HttpRequest updateUser = HttpRequest.newBuilder(new URI(uri))
                .method("PUT", HttpRequest.BodyPublishers.ofString(gson.toJson(userRequest)))
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json; charset=utf-8")
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(updateUser, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    public static void deleteUser() throws URISyntaxException, IOException, InterruptedException {

        String uri = "https://jsonplaceholder.typicode.com/users/23";
        HttpRequest deleteUser = HttpRequest.newBuilder(new URI(uri))
                .DELETE()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> send = httpClient.send(deleteUser, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + send.statusCode());
    }

    public static void getUserById() throws URISyntaxException, IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users/1";
        HttpRequest getUserById = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> send = httpClient.send(getUserById, HttpResponse.BodyHandlers.ofString());
        System.out.println("send.statusCode() = " + send.statusCode());
        System.out.println("send.body() = " + send.body());
    }

    public static void getAllUsers() throws URISyntaxException, IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users";
        HttpRequest getAllUsers = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> send = httpClient.send(getAllUsers, HttpResponse.BodyHandlers.ofString());
        System.out.println("send.statusCode() = " + send.statusCode());
        System.out.println("send.body() = " + send.body());
    }

    public static void getUserByUsername() throws URISyntaxException, IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users?username=Bret";
        HttpRequest getUserByUsername = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> send = httpClient.send(getUserByUsername, HttpResponse.BodyHandlers.ofString());
        System.out.println("send.statusCode() = " + send.statusCode());
        System.out.println("send.body() = " + send.body());
    }

    public static void getAllComments() throws IOException, InterruptedException, URISyntaxException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int userId = 1;

        String uri = "https://jsonplaceholder.typicode.com/users/" + userId + "/posts";
        HttpRequest getAllPosts = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(getAllPosts, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.body() = " + response.body());
    }

    public static void getAllOpenedTasks() throws IOException, InterruptedException, URISyntaxException {
        String uri = "https://jsonplaceholder.typicode.com/users/1/todos";
        HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());

        int userId = 1;

        for (JsonNode node : rootNode) {
            int userIdFromApi = node.get("userId").asInt();
            boolean completed = node.get("completed").asBoolean();

            if (userIdFromApi == userId && !completed) {
                int id = node.get("id").asInt();
                String title = node.get("title").asText();
                System.out.println("userId: " + userId + ", ID: " + id + ", Заголовок: " + title + ", completed: " + completed);
            }
        }
    }

    public static void getAllCommentFromLastPost(long userId) throws URISyntaxException, IOException, InterruptedException {
        String url1 = "https://jsonplaceholder.typicode.com/users/";
        String url2 = "https://jsonplaceholder.typicode.com/posts/";
        Gson GSON = new Gson();

        HttpRequest request1 = HttpRequest.newBuilder(new URI(url1 + userId + "/posts"))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response1 = httpClient.send(request1, HttpResponse.BodyHandlers.ofString());
        User[] users = GSON.fromJson(response1.body(), User[].class);

        Long maxId = Long.valueOf(Arrays.stream(users)
                .max(Comparator.comparing(User::getId))
                .map(User::getId)
                .orElse(null));

        HttpRequest request2 = HttpRequest.newBuilder(new URI(url2 + maxId + "/comments"))
                .GET()
                .build();
        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        List<Post> post = GSON.fromJson(response2.body(), new TypeToken<List<Post>>() {}.getType());

        String fileName = "user-" + userId + "-post-" + maxId + "-comments.json";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            GSON.toJson(post, fileWriter);
        }
    }
}
