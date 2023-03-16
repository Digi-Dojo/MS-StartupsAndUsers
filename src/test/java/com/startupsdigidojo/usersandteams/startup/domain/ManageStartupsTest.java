package com.startupsdigidojo.usersandteams.startup.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ManageStartupsTest {
    private ManageStartups underTest;

    @Mock
    private StartupRepository startupRepository;

    @BeforeEach
    void setUp() {underTest = new ManageStartups(startupRepository);}

    @Test
    public void itCreatesAStartup(){
        Startup startup = new Startup("GreenGym", "An eco-friendly environment to train in");

        when(startupRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(startupRepository.save(any())).thenReturn(new Startup(randomPositiveLong(),startup.getName(),startup.getDescription()));

        Startup result = underTest.createStartup(startup.getName(), startup.getDescription());

        assertThat(result).isInstanceOf(Startup.class);
        assertThat(result.getName()).isEqualTo(startup.getName());
        assertThat(result.getDescription()).isEqualTo(startup.getDescription());
        assertThat(result.getId())
                .isNotNull()
                .isGreaterThan(0);
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
