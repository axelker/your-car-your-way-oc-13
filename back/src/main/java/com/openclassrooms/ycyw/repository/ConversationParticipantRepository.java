package com.openclassrooms.ycyw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.ycyw.model.ConversationParticipantEntity;

public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipantEntity, Long> {}

