package com.dreamteam.ssobbi.message.repository;

import com.dreamteam.ssobbi.message.entity.ScheduledMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledMessageRepository extends JpaRepository<ScheduledMessage, Long> {

}
