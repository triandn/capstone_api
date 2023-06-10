package com.example.capstone_be.repository;

import com.example.capstone_be.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

//    List<Message> findAllByRoom(String room);
}