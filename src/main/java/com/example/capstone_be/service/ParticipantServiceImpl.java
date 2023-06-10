package com.example.capstone_be.service;

import com.example.capstone_be.dto.participant.ParticipantDto;
import com.example.capstone_be.model.Participant;
import com.example.capstone_be.repository.MessageRepository;
import com.example.capstone_be.repository.ParticipantRepository;
import com.example.capstone_be.repository.RoomChatRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final RoomChatRepository roomChatRepository;
    private final MessageRepository messageRepository;
    private final ParticipantRepository participantRepository;
    private final ModelMapper mapper;

    @Override
    public ParticipantDto createParticipant(ParticipantDto participantDto) {
        participantRepository.save(mapper.map(participantDto, Participant.class));
        return participantDto;
    }
}
