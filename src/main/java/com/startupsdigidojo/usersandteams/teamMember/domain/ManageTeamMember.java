package com.startupsdigidojo.usersandteams.teamMember.domain;

import com.startupsdigidojo.usersandteams.user.domain.User;
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
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findById(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("Team Member with the id #" + id + " not found");
        }
        return maybeTeamMember.get();
    }

    public TeamMember findByUserId(Long id){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByPuserId(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("User with id #" + id + " not found");
        }
        return maybeTeamMember.get();
    }

    /*
        A User could appear in more than one Start up
     */

    public List<TeamMember> findAllByUserId(Long id){
        Optional<List<TeamMember>> maybeTeamMember = Optional.of(teamMemberRepository.findAllByPuserId(id));

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
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByPuserName(name);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No Team Member with name #" + name + " present yet");
        }

        return maybeTeamMember.get();
    }

    public TeamMember createTeamMember(User user, String role) {
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByPuserName(user.getName());

        if(maybeTeamMember.isPresent()){
            throw new IllegalArgumentException("No Team with User id #" + user.getId() + " present yet");
        }

        return teamMemberRepository.save(new TeamMember(user, role));
    }

    public void deleteTeamMember(User user) {
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByPuserId(user.getId());

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No Team with User id #" + user.getId() + " present yet");
        }

        teamMemberRepository.delete(maybeTeamMember.get());
    }

    public TeamMember updateTeamMemberRole(Long id, String oldRole, String newRole){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByPuserName(oldRole);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No User with id TeamMember #" + id + " present in any Team yet");
        }

        return teamMemberRepository.save(new TeamMember(maybeTeamMember.get().getPuser(), newRole));
    }
/*
    public void updateTeamMemberRole(User user, String role){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByPuserId(user.getId());

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No User with id #" + user.getId() + " present in any Team yet");
        }

        teamMemberRepository.save(new TeamMember(user, role));
    }
*/

}
