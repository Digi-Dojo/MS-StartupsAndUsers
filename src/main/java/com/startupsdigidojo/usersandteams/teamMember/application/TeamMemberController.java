package com.startupsdigidojo.usersandteams.teamMember.application;

import com.startupsdigidojo.usersandteams.teamMember.domain.ManageTeamMember;
import com.startupsdigidojo.usersandteams.teamMember.domain.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/teammembers")
public class TeamMemberController {
    private ManageTeamMember manageTeamMember;

    @Autowired
    public TeamMemberController(ManageTeamMember manageTeamMember) {
        this.manageTeamMember = manageTeamMember;
    }

    @GetMapping("{id}")
    public TeamMember findById(@PathVariable("id") Long id) {
        return manageTeamMember.findByTeamMemberId(id);
    }

    @PostMapping
    public TeamMember createNewTeamMember(@RequestBody CreateTeamMemberDTO dto) {
        return manageTeamMember.createTeamMember(dto.getUser(), dto.getRole(), dto.getStartup());
    }

    @DeleteMapping(path = "/delete")
    public void deleteTeamMember(@RequestBody CreateTeamMemberDTO dto) {
        manageTeamMember.deleteTeamMember(dto.getUser());
    }

}
