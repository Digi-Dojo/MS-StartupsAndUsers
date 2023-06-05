package com.startupsdigidojo.usersandteams.teamMember.application.kafka;

import com.startupsdigidojo.usersandteams.teamMember.application.event.NewTeamMember;
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
    @Value("${com.startupsdigidojo.usersandteams.teamMember.application.kafka.TeamMemberProducer.topics.new_team_member}")
    private String newTeamMemberTopic;

    @Autowired
    private final KafkaTemplate<String,String> teamMemberKafkaTemplate;

    @Override
    public void emitNewTeamMember(TeamMember teamMember) {
        NewTeamMember newTeamMemberEvent = new NewTeamMember(teamMember);
        teamMemberKafkaTemplate.send(newTeamMemberTopic, newTeamMemberEvent.toJson());
    }
}
