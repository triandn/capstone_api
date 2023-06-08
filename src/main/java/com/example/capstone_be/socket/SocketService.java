package com.example.capstone_be.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.capstone_be.model.Message;
import com.example.capstone_be.service.MessageService;
import com.example.capstone_be.util.enums.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocketService {


    private final MessageService messageService;


    public void sendSocketMessage(SocketIOClient senderClient, String message, String room,String eventName) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.SERVER, message));
            }
        }
    }

    public void saveMessage(SocketIOClient senderClient, Message message) {
//        Message storedMessage = messageService.saveMessage(Message.builder()
//                .messageType(MessageType.CLIENT)
//                .content(message.getContent())
//                .room(message.getRoom())
//                .username(message.getUsername())
//                .build());
//        sendSocketMessage(senderClient, storedMessage, message.getRoom());
    }

    public void saveInfoMessage(SocketIOClient senderClient, String message, String room) {
//        Message storedMessage = messageService.saveMessage(Message.builder()
//                .messageType(MessageType.SERVER)
//                .content(message)
//                .room(room)
//                .build());
//        sendSocketMessage(senderClient, storedMessage, room);
    }
}