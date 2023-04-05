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
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        startup = new Startup("DigiDojo","Startup for digital services");
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
        when(teamMemberRepository.findByPuserIdAndStartupId(anyLong(),anyLong()))
                .thenReturn(Optional.of(new TeamMember(user, role, startup)));


        assertThatThrownBy(() -> underTest.createTeamMember(user.getId(), role, startup.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateTeamMemberRole(){
        String oldRole = "Assistant";
        String newRole = "Manager";
        Long id = randomPositiveLong();


        when(teamMemberRepository.findById(id)).thenReturn(Optional.of(new TeamMember(id, user, oldRole, startup)));
        when(teamMemberRepository.save(any())).thenReturn(new TeamMember(id, user, newRole, startup));

        TeamMember teamMember = underTest.updateTeamMemberRole(id, newRole);

        assertThat(teamMember.getRole()).isEqualTo(newRole);
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
