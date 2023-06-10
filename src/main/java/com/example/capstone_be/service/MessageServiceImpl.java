package com.example.capstone_be.service;


import com.example.capstone_be.dto.message.MessageDto;
import com.example.capstone_be.model.Message;
import com.example.capstone_be.repository.MessageRepository;
import com.example.capstone_be.repository.RoomChatRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final RoomChatRepository roomChatRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper mapper;
    @Override
    public MessageDto createMessage(MessageDto messageDto) {
        messageRepository.save(mapper.map(messageDto, Message.class));
        return messageDto;
    }
}
