package com.startupsdigidojo.usersandteams.teamMember.application.kafka;

import com.startupsdigidojo.usersandteams.teamMember.application.event.StartupAddedUser;
import com.startupsdigidojo.usersandteams.teamMember.application.event.StartupRemovedUser;
import com.startupsdigidojo.usersandteams.teamMember.domain.TeamMember;
import com.startupsdigidojo.usersandteams.teamMember.domain.TeamMemberBroadcaster;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TeamMemberProducer implements TeamMemberBroadcaster {
    @Value("${com.startupsdigidojo.usersandteams.teamMember.application.kafka.TeamMemberProducer.topics.startup.added_user}")
    private String startupAddedUserTopic;

    @Value("${com.startupsdigidojo.usersandteams.teamMember.application.kafka.TeamMemberProducer.topics.startup.removed_user}")
    private String startupRemovedUserTopic;

    @Autowired
    private final KafkaTemplate<String,String> teamMemberKafkaTemplate;

    @Override
    public void emitStartupAddedUser(TeamMember teamMember) {
        StartupAddedUser startupAddedUserEvent = new StartupAddedUser(teamMember);
        teamMemberKafkaTemplate.send(startupAddedUserTopic, startupAddedUserEvent.toJson());
    }

    @Override
    public void emitStartupRemovedUser(TeamMember teamMember) {
        StartupRemovedUser startupRemovedUserEvent = new StartupRemovedUser(teamMember);
        teamMemberKafkaTemplate.send(startupRemovedUserTopic, startupRemovedUserEvent.toJson());
    }
}
