package com.startupsdigidojo.usersandteams.user.application.kafka;

import com.startupsdigidojo.usersandteams.user.application.event.UserCreated;
import com.startupsdigidojo.usersandteams.user.application.event.UserDeleted;
import com.startupsdigidojo.usersandteams.user.application.event.UserLogIn;
import com.startupsdigidojo.usersandteams.user.application.event.UserUpdated;
import com.startupsdigidojo.usersandteams.user.domain.UserBroadcaster;
import com.startupsdigidojo.usersandteams.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserProducer implements UserBroadcaster {
    @Value("${com.startupsdigidojo.usersandteams.user.application.kafka.UserProducer.topics.user.created}")
    private String userCreatedTopic;

    @Value("${com.startupsdigidojo.usersandteams.user.application.kafka.UserProducer.topics.user.logged_in}")
    private String userLogInTopic;

    @Value("${com.startupsdigidojo.usersandteams.user.application.kafka.UserProducer.topics.user.updated}")
    private String userUpdatedTopic;

    @Value("${com.startupsdigidojo.usersandteams.user.application.kafka.UserProducer.topics.user.deleted}")
    private String userDeletedTopic;

    @Autowired
    private final KafkaTemplate<String, String> userKafkaTemplate;

    @Override
    public void emitUserCreated(User user) {
        UserCreated userCreatedEvent = new UserCreated(user);
        userKafkaTemplate.send(userCreatedTopic, userCreatedEvent.toJson());
    }

    @Override
    public void emitUserLogIn(User user) {
        UserLogIn userLogInEvent = new UserLogIn(user);
        userKafkaTemplate.send(userLogInTopic, userLogInEvent.toJson());
    }

    @Override
    public void emitUserUpdated(User user, String attribute) {
        UserUpdated userUpdatedEvent = new UserUpdated(user, attribute);
        userKafkaTemplate.send(userUpdatedTopic, userUpdatedEvent.toJson());
    }

    @Override
    public void emitUserDeleted(User user) {
        UserDeleted userDeletedEvent = new UserDeleted(user);
        userKafkaTemplate.send(userDeletedTopic, userDeletedEvent.toJson());
    }
}
