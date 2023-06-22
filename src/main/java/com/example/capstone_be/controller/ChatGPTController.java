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
    @GetMapping("/get")
    public ResponseEntity<?> getAllDayBooking(@RequestParam String prompt) throws SQLException {
        List<TourViewForChatGPT> response = chatGPTService.getListTourChatGPT(prompt);
        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/chat")
    public ResponseEntity<?> chat(@RequestParam String prompt) {

        String context = "Here is a table containing data about tours, a table named tours and " +
                "fields as follows \n tour_id integer , \n title character , \n rating double precision, " +
                "\n city \n character varying(255), \n price_one_person double precision, \n image_main text , " +
                "\n working text, \n destination_description text , \n created_at timestamp with " +
                "\n time zone DEFAULT now(), \n updated_at timestamp with time zone DEFAULT now(), " +
                "\n latitude double precision, \n longitude double precision, \n time_slot_length " +
                "\n integer, \n time_book_start time without time zone, \n time_book_end time without time zone " +
                "\n Description of the fields: \n tour_id is the primary key of the table, \n title is the title, " +
                "the name of the tour, \n city is the name of the city of the tour, " +
                "\n rating is the rating of the tour, \n price_one_person is the price of the tour, " +
                "\n image_main is the image of the tour, \n working is the activity of the tour, " +
                "\n destination description is the tour description, \n created_at is tour creation time, " +
                "\n updated_at is tour update time, \n latitude is latitude, \n longitude is longitude, " +
                "\n time slot length is the length of time, \n time_book_start \n is the start time of the tour, " +
                "\n time_book_end is the end time of the tour " +
                "\n if the prompt has content related to the fields in the table, " +
                "then create the corresponding postgresql statement with PostgreSQL's format" +
                "and no description needed \n";
        // create a request
        prompt = context+prompt;
        String model = "gpt-3.5-turbo";
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        ChatRequest request = new ChatRequest(model, prompt);
        request.setN(1);
        System.out.println(request.toString());
        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            ResponseEntity.ok()
                    .body(new Message("Error", "No response"));
        }


        // return the first response
        return ResponseEntity.ok()
                .body(response.getChoices().get(0).getMessage().getContent());
    }
}
