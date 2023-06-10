package com.example.capstone_be.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.capstone_be.dto.message.MessageDto;
import com.example.capstone_be.dto.participant.ParticipantDto;
import com.example.capstone_be.dto.roomchat.RoomChatDto;
import com.example.capstone_be.model.Message;
import com.example.capstone_be.service.MessageService;
import com.example.capstone_be.service.ParticipantService;
import com.example.capstone_be.service.RoomChatService;
import com.example.capstone_be.util.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocketService {


    private final MessageService messageService;
    private final RoomChatService roomChatService;
    private final ParticipantService participantService;
    private final ModelMapper mapper;

//    public void sendSocketMessage(SocketIOClient senderClient, String message, String room,String eventName) {
//        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
//            if (!client.getSessionId().equals(senderClient.getSessionId())) {
//                client.sendEvent(eventName,
//                        new Message(MessageType.SERVER, message));
//            }
//        }
//    }
    public void sendSocketMessage(SocketIOClient senderClient, String message, String room) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent("read_message",
                        new Message(MessageType.SERVER.toString(), message));
            }
        }
    }

    public void saveMessage(SocketIOClient senderClient, MessageDto messageDto,
                            RoomChatDto roomChatDto, UUID user_id) {
        MessageDto messageDtoSave = new MessageDto();
        messageDtoSave.setMessage(messageDto.getMessage());
        messageDtoSave.setMessageType(MessageType.CLIENT.toString());
        messageDtoSave.setUsername(messageDto.getUsername());

        RoomChatDto roomChatDtoSave = new RoomChatDto();
        roomChatDtoSave.setRoomName(roomChatDto.getRoomName());

        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setUser_id(user_id);
        participantDto.setRoom_id(roomChatDto.getRoomId());

//        participantService.createParticipant(participantDto);
        messageService.createMessage(messageDtoSave);
        roomChatService.createRoomChat(roomChatDtoSave);
        participantService.createParticipant(participantDto);

        sendSocketMessage(senderClient, messageDto.getMessage(), roomChatDto.getRoomName());
    }

    public void saveInfoMessage(SocketIOClient senderClient, MessageDto messageDto,RoomChatDto roomChatDto) {
        MessageDto messageDtoSave = new MessageDto();
        messageDtoSave.setMessage(messageDto.getMessage());
        messageDtoSave.setMessageType(MessageType.SERVER.toString());
        messageDtoSave.setUsername(messageDto.getUsername());

        RoomChatDto roomChatDtoSave = new RoomChatDto();
        roomChatDtoSave.setRoomName(roomChatDto.getRoomName());

        messageService.createMessage(messageDtoSave);
        roomChatService.createRoomChat(roomChatDtoSave);
        sendSocketMessage(senderClient, messageDto.getMessage(), roomChatDto.getRoomName());

//        sendSocketMessage(senderClient, storedMessage, room);
    }
}