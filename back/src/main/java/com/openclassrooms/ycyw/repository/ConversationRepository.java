package com.openclassrooms.ycyw.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.openclassrooms.ycyw.model.ConversationEntity;

public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {

    @Query("""
    SELECT c FROM ConversationEntity c
    WHERE c.id IN (
        SELECT cp.id.conversationId FROM ConversationParticipantEntity cp
        WHERE cp.id.userId IN :userIds
        GROUP BY cp.id.conversationId
        HAVING COUNT(DISTINCT cp.id.userId) = :userCount
    )
    AND SIZE(c.participants) = :userCount
    """)
    Optional<ConversationEntity> findConversationWithExactParticipants(
        @Param("userIds") List<Long> userIds,
        @Param("userCount") long userCount
    );

    List<ConversationEntity> findAllByParticipantsUserId(Long userId);
}
