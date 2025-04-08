package com.openclassrooms.ycyw.repository;

import org.springframework.data.repository.CrudRepository;

import com.openclassrooms.ycyw.model.MessageEntity;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {}
