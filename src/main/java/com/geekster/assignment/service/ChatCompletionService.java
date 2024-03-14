package com.geekster.assignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekster.assignment.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatCompletionService {

    // Sanity checking of the prompt is done via Hibernate validator and this service method does not need any internal
    // processing of the prompt.
    public ResponseEntity<Response> completeChat(String prompt) throws IOException, InterruptedException {
        System.out.println("-".repeat(30));
        System.out.println("PROCESSING NEW REQUEST");
        System.out.println("Current prompt: " + prompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgpt-ai-chat-bot.p.rapidapi.com/ask"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", System.getenv("RAPID_API_KEY"))
                .header("X-RapidAPI-Host", "chatgpt-ai-chat-bot.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(prepareRequestBody(prompt, 256)))
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response from RapidAPI: " + response.body());
        Optional<String> textResponse = getMessage(response.body());

        System.out.println("-------------------------------------------------");
        return generateResponse(textResponse.isEmpty()? "Internal server error": textResponse.get());
}

    private String prepareRequestBody(String prompt, int wordLimit) {
        return "{\r\"query\": \"" + prompt + "\",\r \"wordLimit\": \"" + wordLimit + "\"\r}";
    }

    private Optional<String> getMessage(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }

        return Optional.of(
                jsonNode.get("response").asText()
        );
    }

    private ResponseEntity<Response> generateResponse(String response) {
        final Response chatResponse = new Response(
                response,
                LocalDateTime.now(),
                response.equals("Internal Server error")? HttpStatus.BAD_REQUEST.value() : HttpStatus.OK.value()
        );

        return switch(chatResponse.getResponseCode()) {
            case 400 -> ResponseEntity.badRequest().body(chatResponse);
            case 200 -> ResponseEntity.ok(chatResponse);
            default -> null;
        };
    }
}
