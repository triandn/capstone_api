package com.example.capstone_be.service;


import com.example.capstone_be.dto.message.MessageDto;

public interface MessageService {
    MessageDto createMessage(MessageDto messageDto);
}
