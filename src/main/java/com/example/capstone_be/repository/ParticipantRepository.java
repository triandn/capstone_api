package com.example.capstone_be.repository;

import com.example.capstone_be.model.Message;
import com.example.capstone_be.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}
