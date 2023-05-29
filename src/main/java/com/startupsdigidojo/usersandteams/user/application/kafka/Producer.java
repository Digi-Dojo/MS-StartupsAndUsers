package com.startupsdigidojo.usersandteams.user.application.kafka;

import com.startupsdigidojo.usersandteams.user.application.event.NewUser;
import com.startupsdigidojo.usersandteams.user.domain.Broadcaster;
import com.startupsdigidojo.usersandteams.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Producer implements Broadcaster {
    @Value("${com.startupsdigidojo.usersandteams.user.application.kafka.producer.topics.new_user}")
    private String newUserTopic;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void emitNewUser(User user){
        NewUser newUserEvent = new NewUser(user);
        kafkaTemplate.send(newUserTopic, newUserEvent.toJson());
    }
}
