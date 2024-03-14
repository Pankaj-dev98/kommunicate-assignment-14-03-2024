package com.geekster.assignment.controllers;

import com.geekster.assignment.service.ChatCompletionService;
import com.geekster.assignment.util.PromptDto;
import com.geekster.assignment.util.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")

@RequiredArgsConstructor
public class ChatCompletionController {
    private final ChatCompletionService service;

    @PostMapping("/complete_chat")
    public ResponseEntity<Response> completeChat(@RequestBody @Valid PromptDto prompt) throws IOException, InterruptedException {
        return service.completeChat(prompt.getPrompt());
    }
}
