package com.example.capstone_be.service;

import com.example.capstone_be.dto.roomchat.RoomChatDto;
import com.example.capstone_be.model.RoomChat;
import com.example.capstone_be.repository.RoomChatRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomChatServiceImpl implements RoomChatService{
    private final RoomChatRepository roomChatRepository;
    private final ModelMapper mapper;
    @Override
    public RoomChatDto createRoomChat(RoomChatDto roomChatDto) {
        roomChatRepository.save(mapper.map(roomChatDto, RoomChat.class));
        return roomChatDto;
    }
}
