package com.example.capstone_be.repository;

import com.example.capstone_be.model.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomChatRepository extends JpaRepository<RoomChat, UUID> {
}
