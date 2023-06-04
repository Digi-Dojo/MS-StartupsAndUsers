package com.startupsdigidojo.usersandteams.startup.application.kafka;

import com.startupsdigidojo.usersandteams.startup.application.event.NewStartup;
import com.startupsdigidojo.usersandteams.startup.application.event.StartupDelete;
import com.startupsdigidojo.usersandteams.startup.application.event.StartupDescriptionUpdate;
import com.startupsdigidojo.usersandteams.startup.application.event.StartupNameUpdate;
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
    public void emitNewStartup(Startup startup) {
        NewStartup newStartupEvent = new NewStartup(startup);
        startupKafkaTemplate.send(newStartupTopic, newStartupEvent.toJson());
    }

    @Override
    public void emitStartupNameUpdate(Startup startup) {
        StartupNameUpdate startupNameUpdateEvent = new StartupNameUpdate(startup);
        startupKafkaTemplate.send(startupNameUpdateTopic, startupNameUpdateEvent.toJson());
    }

    @Override
    public void emitStartupDescriptionUpdate(Startup startup) {
        StartupDescriptionUpdate startupDescriptionUpdateEvent = new StartupDescriptionUpdate(startup);
        startupKafkaTemplate.send(startupDescriptionUpdateTopic, startupDescriptionUpdateEvent.toJson());
    }

    @Override
    public void emitStartupDelete(Startup startup) {
        StartupDelete startupDeleteEvent = new StartupDelete(startup);
        startupKafkaTemplate.send(startupDeleteTopic, startupDeleteEvent.toJson());
    }
}
