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
public class UserProducer implements Broadcaster {
    @Value("${com.startupsdigidojo.usersandteams.user.application.kafka.UserProducer.topics.new_user}")
    private String newUserTopic;

    @Autowired
    private final KafkaTemplate<String, String> userKafkaTemplate;

    @Override
    public void emitNewUser(User user){
        NewUser newUserEvent = new NewUser(user);
        userKafkaTemplate.send(newUserTopic, newUserEvent.toJson());
    }
}
