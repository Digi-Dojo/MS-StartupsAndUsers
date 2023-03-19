package com.startupsdigidojo.usersandteams.application.TeamMemberDTOandController;

import com.startupsdigidojo.usersandteams.domain.Teams.ManageTeamMember;
import com.startupsdigidojo.usersandteams.domain.Teams.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/battleships")
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
        return manageTeamMember.createTeamMember(dto.getUser(), dto.getRole());
    }

}
