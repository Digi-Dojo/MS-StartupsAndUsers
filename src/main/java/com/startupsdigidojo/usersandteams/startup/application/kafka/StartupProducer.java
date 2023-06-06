package com.startupsdigidojo.usersandteams.startup.application.kafka;

import com.startupsdigidojo.usersandteams.startup.application.event.StartupCreated;
import com.startupsdigidojo.usersandteams.startup.application.event.StartupDeleted;
import com.startupsdigidojo.usersandteams.startup.application.event.StartupUpdated;
import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import com.startupsdigidojo.usersandteams.startup.domain.StartupBroadcaster;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StartupProducer implements StartupBroadcaster {

    @Value("${com.startupsdigidojo.usersandteams.startup.application.kafka.StartupProducer.topics.new_startup}")
    private String newStartupTopic;

    @Value("${com.startupsdigidojo.usersandteams.startup.application.kafka.StartupProducer.topics.startup_name_update}")
    private String startupNameUpdateTopic;

    @Value("${com.startupsdigidojo.usersandteams.startup.application.kafka.StartupProducer.topics.startup_description_update}")
    private String startupDescriptionUpdateTopic;

    @Value("${com.startupsdigidojo.usersandteams.startup.application.kafka.StartupProducer.topics.startup_delete}")
    private String startupDeleteTopic;



    @Autowired
    private final KafkaTemplate<String, String> startupKafkaTemplate;

    @Override
    public void emitStartupCreated(Startup startup) {
        StartupCreated startupCreatedEvent = new StartupCreated(startup);
        startupKafkaTemplate.send(newStartupTopic, startupCreatedEvent.toJson());
    }



    @Override
    public void emitStartupUpdated(Startup startup) {
        StartupUpdated startupUpdatedEvent = new StartupUpdated(startup);
        startupKafkaTemplate.send(startupDescriptionUpdateTopic, startupUpdatedEvent.toJson());
    }

    @Override
    public void emitStartupDeleted(Startup startup) {
        StartupDeleted startupDeletedEvent = new StartupDeleted(startup);
        startupKafkaTemplate.send(startupDeleteTopic, startupDeletedEvent.toJson());
    }
}
