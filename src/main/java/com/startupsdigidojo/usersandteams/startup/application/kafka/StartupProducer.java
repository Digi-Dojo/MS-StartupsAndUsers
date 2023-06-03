package com.startupsdigidojo.usersandteams.startup.application.kafka;

import com.startupsdigidojo.usersandteams.startup.application.event.NewStartup;
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

    @Autowired
    private final KafkaTemplate<String, String> startupKafkaTemplate;

    @Override
    public void emitNewStartup(Startup startup) {
        NewStartup newStartupEvent = new NewStartup(startup);
        startupKafkaTemplate.send(newStartupTopic, newStartupEvent.toJson());
    }
}
