package com.startupsdigidojo.usersandteams.teamMember.domain;

import com.startupsdigidojo.usersandteams.startup.domain.SearchStartups;
import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import com.startupsdigidojo.usersandteams.startup.domain.StartupRepository;
import com.startupsdigidojo.usersandteams.user.domain.SearchUsers;
import com.startupsdigidojo.usersandteams.user.domain.User;
import com.startupsdigidojo.usersandteams.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ManageTeamMember {
    private TeamMemberRepository teamMemberRepository;
    private SearchUsers searchUsers;
    private SearchStartups searchStartups;


    @Autowired
    public ManageTeamMember(TeamMemberRepository teamMemberRepository, UserRepository userRepository,
                            StartupRepository startupRepository){
        this.teamMemberRepository = teamMemberRepository;
        searchUsers = new SearchUsers(userRepository);
        searchStartups = new SearchStartups(startupRepository);
    }

    public TeamMember findByTeamMemberId(Long id){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findById(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("Team Member with the id #" + id + " not found");
        }
        return maybeTeamMember.get();
    }

    /*
        A User could appear in more than one Start up
     */

    public List<TeamMember> findAllByUserId(Long id){
        searchUsers.findById(id);
        Optional<List<TeamMember>> maybeTeamMember = teamMemberRepository.findAllByPuserId(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("Team Members with user id #" + id + " not found");
        }
        return maybeTeamMember.get();
    }

    public List<TeamMember> findByRole(String role){
        Optional<List<TeamMember>> maybeExistRole = teamMemberRepository.findAllByRole(role);

        if(maybeExistRole.isEmpty()){
            throw new IllegalArgumentException("Team Member with role #" + role + " not found");
        }
        return maybeExistRole.get();
    }

    public List<TeamMember> findTeamMembersByStartupId(Long startupId){
        searchStartups.findById(startupId);
        Optional<List<TeamMember>> maybeTeamMembers = teamMemberRepository.findTeamMembersByStartupId(startupId);

        if(maybeTeamMembers.isEmpty()){
            throw new IllegalArgumentException("No Team Members belonging to startup with Id " + startupId);
        }

        return maybeTeamMembers.get();
    }

    public TeamMember createTeamMember(Long userId, String role, Long startupId){
        User user = searchUsers.findById(userId);
        Startup startup = searchStartups.findById(startupId);
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findByPuserIdAndStartupId(user.getId(), startup.getId());

        if(maybeTeamMember.isPresent()){
            throw new IllegalArgumentException("A Team Member with User id #" + user.getId() + " and Startup id # "
                    + startup.getId() + " is already present");
        }

        return teamMemberRepository.save(new TeamMember(user, role, startup));
    }

    public void deleteTeamMember(Long id) {
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findById(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No Team with id #" + id + " present yet");
        }

        teamMemberRepository.delete(maybeTeamMember.get());
    }

    public TeamMember updateTeamMemberRole(Long id, String newRole){
        Optional<TeamMember> maybeTeamMember = teamMemberRepository.findById(id);

        if(maybeTeamMember.isEmpty()){
            throw new IllegalArgumentException("No User with id TeamMember #" + id + " present in any Team yet");
        }

        return teamMemberRepository.save(new TeamMember(maybeTeamMember.get().getPuser(), newRole, maybeTeamMember.get().getStartup()));
    }


}
