package com.startupsdigidojo.usersandteams.teamMember.domain;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    Optional<List<TeamMember>> findAllByPuserId(Long id);
    Optional<List<TeamMember>> findAllByRole(String role);
    Optional<List<TeamMember>> findTeamMembersByStartupId(Long id);
    Optional<TeamMember> findByPuserIdAndStartupId(Long pId, Long sId);
}
