package com.startupsdigidojo.usersandteams.domain.Teams;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    Optional<TeamMember> findByTeamMemberId(Long teamMemberID);
    Optional<List<TeamMember>> findByRole(String role);
    Optional<List<TeamMember>> findUsersRole(Long id);
    Optional<List<TeamMember>> findByUserId(Long id);
}