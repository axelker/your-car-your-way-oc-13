package com.openclassrooms.ycyw.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.openclassrooms.ycyw.model.SupportMessageEntity;

public interface SupportMessageRepository extends CrudRepository<SupportMessageEntity, Long> {
    //TODO: Delete this query and Use conversation repository.

    @Query("""
        SELECT m FROM SupportMessageEntity m
        WHERE (m.sender.id = :user1 AND m.receiver.id = :user2)
        OR (m.sender.id = :user2 AND m.receiver.id = :user1)
        ORDER BY m.sentAt ASC
    """)
    List<SupportMessageEntity> findChatBetweenUsers(@Param("user1") Long user1, @Param("user2") Long user2);
}
