package com.startupsdigidojo.usersandteams.teamMember.application;

import com.startupsdigidojo.usersandteams.teamMember.domain.ManageTeamMember;
import com.startupsdigidojo.usersandteams.teamMember.domain.TeamMember;
import com.startupsdigidojo.usersandteams.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/teammembers")
public class TeamMemberController {
    private final ManageTeamMember manageTeamMember;

    @Autowired
    public TeamMemberController(ManageTeamMember manageTeamMember) {
        this.manageTeamMember = manageTeamMember;
    }

    @GetMapping("/{id}")
    public TeamMember findById(@PathVariable("id") Long id) {
        return manageTeamMember.findByTeamMemberId(id);
    }

    @GetMapping("/findByUSIds")
    public TeamMember findByUserIdAndStartupId(@RequestBody FindTeamMemberByUserIdAndStartupIdDTO dto) {
        return manageTeamMember.findByUserIdAndStartupId(dto.getUserId(), dto.getStartupId());
    }

//    @GetMapping("/startup/getAllUsers")
//    public List<User> findAllUsersByStartupId(@RequestBody FindUsersByStartupIdDTO dto){
//        return manageTeamMember.findUsersByStartupId(dto.getId());
//    }

    @GetMapping("/startup/getAllUsers/{startupId}")
    public List<User> findUsersByStartupId(@PathVariable("startupId") Long startupId) {return manageTeamMember.findUsersByStartupId(startupId);}

    @PostMapping("/create")
    public TeamMember createNewTeamMember(@RequestBody CreateTeamMemberDTO dto) {
        return manageTeamMember.createTeamMember(dto.getUserId(), dto.getRole(), dto.getStartupId());
    }

    @DeleteMapping( "/delete")
    public void deleteTeamMember(@RequestBody DeleteTeamMemberDTO dto) {
        manageTeamMember.deleteTeamMember(dto.getId());
    }

}
