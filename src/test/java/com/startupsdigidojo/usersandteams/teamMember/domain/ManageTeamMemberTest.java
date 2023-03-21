package com.startupsdigidojo.usersandteams.teamMember.domain;

import com.startupsdigidojo.usersandteams.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManageTeamMemberTest {
    private ManageTeamMember underTest;
    User user;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @BeforeEach
    void setUp() {
        underTest = new ManageTeamMember(teamMemberRepository);
        user = new User("Pippo", "pippo@unibz.it", "password");
    }

    @Test
    public void itCreatesATeamMember() {

        String role = "Software Developer";
        user.setId(randomPositiveLong());
        TeamMember teamMember = new TeamMember(user, role);

        when(teamMemberRepository.findByPuserName(anyString()))
                .thenReturn(Optional.empty());
        when(teamMemberRepository.save(any()))
                .thenReturn(new TeamMember(user, teamMember.getRole()));

        TeamMember result = underTest.createTeamMember(user, role);

        assertThat(result).isInstanceOf(TeamMember.class);
        assertThat(result.getPuser().getName()).isEqualTo(user.getName());
        assertThat(result.getPuser().getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void itThrowsForDuplicationInId() {

        User user = new User("Pippo", "pippo@unibz.it", "password");
        String role = "Software Developer";
        user.setId(randomPositiveLong());

        when(teamMemberRepository.findByPuserName(anyString()))
                .thenReturn(Optional.of(new TeamMember(user, role)));


        assertThatThrownBy(() -> underTest.createTeamMember(user, role))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateTeamMemberRole(){
        User user = new User("Giamma", "giamma@unibz.it", "pswrd");
        String oldRole = "Assistant";
        String newRole = "Manager";
        Long id = randomPositiveLong();


        when(teamMemberRepository.findByPuserName(oldRole)).thenReturn(Optional.of(new TeamMember(id, user, oldRole)));
        when(teamMemberRepository.save(any())).thenReturn(new TeamMember(id, user, newRole));

        TeamMember teamMember = underTest.updateTeamMemberRole(id, oldRole, newRole);

        assertThat(teamMember.getRole()).isEqualTo(newRole);
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
