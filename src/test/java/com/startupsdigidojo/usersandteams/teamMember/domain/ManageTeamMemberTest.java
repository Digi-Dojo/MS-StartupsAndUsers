package com.startupsdigidojo.usersandteams.teamMember.domain;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import com.startupsdigidojo.usersandteams.startup.domain.StartupRepository;
import com.startupsdigidojo.usersandteams.user.domain.User;
import com.startupsdigidojo.usersandteams.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.Comparator.comparing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManageTeamMemberTest {
    private ManageTeamMember underTest;
    User user;
    Startup startup;

    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StartupRepository startupRepository;

    @BeforeEach
    void setUp() {
        underTest = new ManageTeamMember(teamMemberRepository, userRepository, startupRepository);
        user = new User("Pippo", "pippo@unibz.it", "password");
        user.setId(randomPositiveLong());
        startup = new Startup("DigiDojo", "Startup for digital services");
        startup.setId(randomPositiveLong());

    }

    @Test
    public void itCreatesATeamMember() {
        String role = "Software Developer";

        TeamMember teamMember = new TeamMember(user, role, startup);

        when(teamMemberRepository.findByPuserIdAndStartupId(user.getId(), startup.getId()))
                .thenReturn(Optional.empty());
        when(teamMemberRepository.save(any()))
                .thenReturn(new TeamMember(user, teamMember.getRole(), startup));

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));
        TeamMember result = underTest.createTeamMember(user.getId(), role, startup.getId());

        assertThat(result).isInstanceOf(TeamMember.class);
        assertThat(result.getPuser().getName()).isEqualTo(user.getName());
        assertThat(result.getPuser().getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void itThrowsForDuplicationInId() {
        String role = "Software Developer";
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));
        when(teamMemberRepository.findByPuserIdAndStartupId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new TeamMember(user, role, startup)));


        assertThatThrownBy(() -> underTest.createTeamMember(user.getId(), role, startup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateTeamMemberRoleUpdatesTheRole() {
        String oldRole = "Assistant";
        String newRole = "Manager";
        Long id = randomPositiveLong();


        when(teamMemberRepository.findById(id))
                .thenReturn(Optional.of(new TeamMember(id, user, oldRole, startup)));
        when(teamMemberRepository.save(any()))
                .thenReturn(new TeamMember(id, user, newRole, startup));

        TeamMember teamMember = underTest.updateTeamMemberRole(id, newRole);

        assertThat(teamMember.getRole()).isEqualTo(newRole);
    }

    @Test
    public void updateTeamMemberRoleThrowsForNonExistingTeamMember() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.updateTeamMemberRole(randomPositiveLong(), "role"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteThrowsForNonExistingTeamMember() {
        when(teamMemberRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.deleteTeamMember(randomPositiveLong()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByUserIdAndStartupIdReturnsTheTeamMember() {
        when(teamMemberRepository.findByPuserIdAndStartupId(user.getId(), startup.getId()))
                .thenReturn(Optional.of(new TeamMember(user, "role", startup)));
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));

        TeamMember result = underTest.findByUserIdAndStartupId(user.getId(), startup.getId());

        assertThat(result).isInstanceOf(TeamMember.class);
        assertThat(result.getPuser().getId())
                .isNotNull()
                .isEqualTo(user.getId());
        assertThat(result.getStartup().getId())
                .isNotNull()
                .isEqualTo(startup.getId());
        assertThat(result.getRole())
                .isEqualTo("role");
    }

    @Test
    public void findByUserIdAndStartupIdThrowsForNonExistingUser() {
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findByUserIdAndStartupId(user.getId(), startup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByUserIdAndStartupIdThrowsForNonExistingStartup() {
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findByUserIdAndStartupId(user.getId(), startup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByUserIdAndStartupIdThrowsForNonExistingTeamMember() {
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(teamMemberRepository.findByPuserIdAndStartupId(user.getId(), startup.getId()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findByUserIdAndStartupId(user.getId(), startup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findUsersByStartupIdReturnsListOfUsers() {
        User user1 = new User("Matteo", "malarcher@unibz.it", "pass");
        do {
            user1.setId(randomPositiveLong());
        } while (Objects.equals(user1.getId(), user.getId()));
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));
        when(teamMemberRepository.findTeamMembersByStartupId(startup.getId()))
                .thenReturn(Optional.of(new ArrayList<>() {{
                    add(new TeamMember(user, "designer", startup));
                    add(new TeamMember(user1, "developer", startup));
                }}));
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(userRepository.findById(user1.getId()))
                .thenReturn(Optional.of(user1));
        List<User> users = new ArrayList<>() {{
            add(user);
            add(user1);
        }};
        users = users.stream()
                .sorted(comparing(User::getId))
                .toList();

        List<User> result = underTest.findUsersByStartupId(startup.getId()).stream()
                .sorted(comparing(User::getId))
                .toList();

        List<User> finalUsers = users;

        assertThat(result).isNotNull();
        result.forEach(o -> assertEquals(o.getId(), finalUsers.get(result.indexOf(o)).getId()));
    }

    @Test
    public void findTeamMembersByStartupIdReturnsListOfTeamMembers() {
        User user1 = new User("Matteo", "malarcher@unibz.it", "pass");
        do {
            user1.setId(randomPositiveLong());
        } while (Objects.equals(user1.getId(), user.getId()));
        List<TeamMember> teamMembers = new ArrayList<>() {{
            add(new TeamMember(user.getId(), user, "designer", startup));
            add(new TeamMember(user1.getId(), user1, "developer", startup));
        }};

        teamMembers = teamMembers.stream()
                .sorted(comparing(TeamMember::getId))
                .toList();

        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));
        when(teamMemberRepository.findTeamMembersByStartupId(startup.getId()))
                .thenReturn(Optional.of(teamMembers));

        List<TeamMember> result = underTest.findTeamMembersByStartupId(startup.getId());

        List<TeamMember> finalTeamMembers = teamMembers;
        assertThat(result).isNotNull();
        result.forEach(o -> assertEquals(o.getId(), finalTeamMembers.get(result.indexOf(o)).getId()));
    }

    @Test
    public void findTeamMembersByStartupIdThrowsForNonExistingTeamMembersOfSuchStartup() {
        when(startupRepository.findById(startup.getId()))
                .thenReturn(Optional.of(startup));
        when(teamMemberRepository.findTeamMembersByStartupId(startup.getId()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findTeamMembersByStartupId(startup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByRoleReturnsListOfTeamMembers() {
        Startup startup1 = new Startup("startup1", "description1");
        do {
            startup1.setId(randomPositiveLong());
        } while (Objects.equals(startup1.getId(), startup.getId()));
        List<TeamMember> teamMembers = new ArrayList<>() {{
            add(new TeamMember(startup.getId(), user, "designer", startup));
            add(new TeamMember(startup1.getId(), user, "designer", startup1));
        }};
        teamMembers = teamMembers.stream()
                .sorted(comparing(TeamMember::getId))
                .toList();
        when(teamMemberRepository.findAllByRole("designer"))
                .thenReturn(Optional.of(teamMembers));

        List<TeamMember> result = underTest.findByRole("designer");

        List<TeamMember> finalTeamMembers = teamMembers;
        assertThat(result).isNotNull();
        result.forEach(o -> assertEquals(o.getId(), finalTeamMembers.get(result.indexOf(o)).getId()));
    }

    @Test
    public void findByRoleThrowsForNonExistingTeamMembersWithSuchRole() {
        when(teamMemberRepository.findAllByRole(anyString()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findByRole("designer"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findAllByUserIdReturnsListOfTeamMembers() {
        Startup startup1 = new Startup("startup1", "description1");
        do {
            startup1.setId(randomPositiveLong());
        } while (Objects.equals(startup1.getId(), startup.getId()));
        List<TeamMember> teamMembers = new ArrayList<>() {{
            add(new TeamMember(startup.getId(), user, "designer", startup));
            add(new TeamMember(startup1.getId(), user, "developer", startup1));
        }};
        teamMembers = teamMembers.stream()
                .sorted(comparing(TeamMember::getId))
                .toList();

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(teamMemberRepository.findAllByPuserId(user.getId()))
                .thenReturn(Optional.of(teamMembers));

        List<TeamMember> result = underTest.findAllByUserId(user.getId());

        List<TeamMember> finalTeamMembers = teamMembers;
        assertThat(result).isNotNull();
        result.forEach(o -> assertEquals(o.getId(), finalTeamMembers.get(result.indexOf(o)).getId()));
    }

    @Test
    public void findAllByUserIdThrowsForNonExistingTeamMembers() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user));
        when(teamMemberRepository.findAllByPuserId(anyLong()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findAllByUserId(randomPositiveLong()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByTeamMemberIdReturnsTeamMember() {
        TeamMember teamMember = new TeamMember(randomPositiveLong(), user, "designer", startup);
        when(teamMemberRepository.findById(teamMember.getId()))
                .thenReturn(Optional.of(teamMember));
        TeamMember result = underTest.findByTeamMemberId(teamMember.getId());
        assertThat(result)
                .isInstanceOf(TeamMember.class)
                .isNotNull();
        assertEquals(result.getId(), teamMember.getId());
        assertEquals(result.getPuser().getId(), teamMember.getPuser().getId());
        assertEquals(result.getRole(), teamMember.getRole());
        assertEquals(result.getStartup().getId(), teamMember.getStartup().getId());
    }

    @Test
    public void findByTeamMemberIdThrowsForNonExistingTeamMembers() {
        when(teamMemberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findByTeamMemberId(randomPositiveLong()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
