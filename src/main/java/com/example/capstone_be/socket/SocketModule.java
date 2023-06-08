package com.example.capstone_be.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.capstone_be.model.Message;
import com.example.capstone_be.util.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j
public class SocketModule {


    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());

    }


    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.sendSocketMessage(senderClient,data.getMessage(),data.getRoom(),"get_message");
//            socketService.saveMessage(senderClient, data);
        };
    }


    private ConnectListener onConnected() {
//        return (client) -> {
////            String room = client.getHandshakeData().getSingleUrlParam("room");
////            String username = client.getHandshakeData().getSingleUrlParam("room");
//            var params = client.getHandshakeData().getUrlParams();
//            String room = params.get("room").stream().collect(Collectors.joining());
//            String username = params.get("username").stream().collect(Collectors.joining());
//            client.joinRoom(room);
//            socketService.saveInfoMessage(client, String.format(Constants.WELCOME_MESSAGE, username), room);
//            log.info("Socket ID[{}] - room[{}] - username [{}]  Connected to chat module through", client.getSessionId().toString(), room, username);
//        };
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
//        return client -> {
//            var params = client.getHandshakeData().getUrlParams();
//            String room = params.get("room").stream().collect(Collectors.joining());
//            String username = params.get("username").stream().collect(Collectors.joining());
//            socketService.saveInfoMessage(client, String.format(Constants.DISCONNECT_MESSAGE, username), room);
//            log.info("Socket ID[{}] - room[{}] - username [{}]  discnnected to chat module through", client.getSessionId().toString(), room, username);
//        };
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }


}
