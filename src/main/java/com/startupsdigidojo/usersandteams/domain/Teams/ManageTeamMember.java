package com.startupsdigidojo.usersandteams.domain.Teams;

import com.startupsdigidojo.usersandteams.domain.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ManageTeamMember {
    private TeamMemberRepository teamMemberRepository;
    private TeamMemberRepository teamMemberRepositoryMultiple;

    @Autowired
    public ManageTeamMember(TeamMemberRepository teamMemberRepository, TeamMemberRepository teamMemberRepositoryMultiple){
        this.teamMemberRepository = teamMemberRepository;
        this.teamMemberRepositoryMultiple = teamMemberRepositoryMultiple;
    }

    public TeamMember findByTeamMemberId(Long id){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByTeamMemberId(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("Team Member #" + id + "not found");
        }
        return maybeTeamMember.get();
    }

    public List<TeamMember> findByRole(String role){
        Optional<List<TeamMember>> maybeExistRole = teamMemberRepositoryMultiple.findByRole(role);

        if(maybeExistRole.isEmpty()){
            throw new IllegalArgumentException("Team Member with role #" + role + "not found");
        }
        return maybeExistRole.get();
    }

    public List<TeamMember> findUsersRole(Long id){
        Optional<List<TeamMember>> maybeHasMultipleRole = teamMemberRepositoryMultiple.findUsersRole(id);

        if(maybeHasMultipleRole.isEmpty()){
            throw new IllegalArgumentException("Team User with id #" + id + "doesn't have any role");
        }
        return maybeHasMultipleRole.get();
    }

    public TeamMember createTeamMember(User user, String role) {
        //Todo: could a user participate in more than one start up?
        return null;
    }
}
