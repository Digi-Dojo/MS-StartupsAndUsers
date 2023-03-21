package com.startupsdigidojo.usersandteams.domain.TeamMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByTeamMemberId(Long teamMemberID);
    Optional<TeamMember> findByPuserId(Long id);
    List<TeamMember> findAllByTeamMemberId(Long id);
    Optional<List<TeamMember>> findByRole(String role);
    Optional<List<TeamMember>> findUsersByRole(String role);
    Optional<TeamMember> findByPuserName(String name);
}
