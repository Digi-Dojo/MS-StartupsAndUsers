package com.startupsdigidojo.usersandteams.domain.Teams;

import com.startupsdigidojo.usersandteams.domain.User.*;
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

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @BeforeEach
    void setUp() {
        underTest = new ManageTeamMember(teamMemberRepository);
    }

    @Test
    public void itCreatesATeamMember() {
        // given

        User user = new User("Pippo", "pippo@unibz.it", "password");
        String role = "Software Developer";
        user.setId(randomPositiveLong());
        TeamMember teamMember = new TeamMember(user, role);

        when(teamMemberRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        when(teamMemberRepository.save(any()))
                .thenReturn(new TeamMember(user, teamMember.getRole()));

        TeamMember result = underTest.createTeamMember(user, role);

        assertThat(result).isInstanceOf(TeamMember.class);
        assertThat(result.getUser().getName()).isEqualTo(user.getName());
        assertThat(result.getUser().getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    @Test
    public void itThrowsForDuplicationInId() {

        User user = new User("Pippo", "pippo@unibz.it", "password");
        String role = "Software Developer";
        user.setId(randomPositiveLong());

        when(teamMemberRepository.findByName(anyString()))
                .thenReturn(Optional.of(new TeamMember(user, role)));


        assertThatThrownBy(() -> underTest.createTeamMember(user, role))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
