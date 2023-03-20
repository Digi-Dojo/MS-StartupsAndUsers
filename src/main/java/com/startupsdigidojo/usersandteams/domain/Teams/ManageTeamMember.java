package com.startupsdigidojo.usersandteams.domain.Teams;

import com.startupsdigidojo.usersandteams.domain.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ManageTeamMember {
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    public ManageTeamMember(TeamMemberRepository teamMemberRepository){
        this.teamMemberRepository = teamMemberRepository;
    }

    public TeamMember findByTeamMemberId(Long id){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByTeamMemberId(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("Team Member with the id #" + id + " not found");
        }
        return maybeTeamMember.get();
    }

    /*
        A User could appear in more than one Start up
     */

    public List<TeamMember> findAllByUserId(Long id){
        Optional<List<TeamMember>> maybeTeamMember = Optional.of(teamMemberRepository.findAllById(id));

        if(maybeTeamMember.get().isEmpty()){
            throw new IllegalArgumentException("Users with id #" + id + " not found");
        }
        return maybeTeamMember.get();
    }

    public List<TeamMember> findByRole(String role){
        Optional<List<TeamMember>> maybeExistRole = teamMemberRepository.findByRole(role);

        if(maybeExistRole.isEmpty()){
            throw new IllegalArgumentException("Team Member with role #" + role + " not found");
        }
        return maybeExistRole.get();
    }

    public List<TeamMember> findUsersByRole(String role){
        Optional<List<TeamMember>> maybeHasMultipleRole = teamMemberRepository.findUsersByRole(role);

        if(maybeHasMultipleRole.isEmpty()){
            throw new IllegalArgumentException("No Team Member with role #" + role + " present yet");
        }

        return maybeHasMultipleRole.get();
    }

    public TeamMember findByName(String name){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByName(name);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No Team Member with name #" + name + " present yet");
        }

        return maybeTeamMember.get();
    }

    public TeamMember createTeamMember(User user, String role) {
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByName(user.getName());

        if(maybeTeamMember.isPresent()){
            throw new IllegalArgumentException("No Team with User id #" + user.getId() + " present yet");
        }

        return teamMemberRepository.save(new TeamMember(user, role));
    }

    public void deleteTeamMember(User user) {
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByUserId(user.getId());

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No Team with User id #" + user.getId() + " present yet");
        }

        teamMemberRepository.delete(maybeTeamMember.get());
    }

    public void updateTeamMemberRole(User user, String role){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByUserId(user.getId());

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No User with id #" + user.getId() + " present in any Team yet");
        }

        teamMemberRepository.save(new TeamMember(user, role));
    }


}
