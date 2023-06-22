package com.example.capstone_be.controller;

import com.example.capstone_be.dto.chat.ChatGPTDto;
import com.example.capstone_be.dto.chat.ChatRequest;
import com.example.capstone_be.dto.chat.ChatResponse;
import com.example.capstone_be.dto.chat.Message;
import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.tour.TourViewForChatGPT;
import com.example.capstone_be.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/gpt")
public class ChatGPTController {
    private final ChatGPTService chatGPTService;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }
    @PostMapping("/chat")
    public ResponseEntity<?> getAllDayBooking(@RequestBody ChatGPTDto chatGPTDto) throws SQLException {
        List<TourViewForChatGPT> response = chatGPTService.getListTourChatGPT(chatGPTDto.getMessage());
        return ResponseEntity.ok()
                .body(response);
    }

}
