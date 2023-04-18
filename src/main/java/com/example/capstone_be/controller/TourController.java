package com.example.capstone_be.controller;


import com.example.capstone_be.dto.user.JwtRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour")
public class TourController {
    @GetMapping("/tour/all")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
