package com.startupsdigidojo.usersandteams.startup.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class SearchStartupsTest {
    private SearchStartups underTest;

    @Mock
    private StartupRepository startupRepository;

    @BeforeEach
    void setUp() {
        underTest = new SearchStartups(startupRepository);
    }

    @Test
    public void findByIdReturnsStartupWithMatchingId() {
        Long id = randomPositiveLong();
        String name = "name";
        String description = "description";
        when(startupRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Startup(id, name, description)));

        Startup result = underTest.findById(id);

        assertThat(result)
                .isInstanceOf(Startup.class);
        assertThat(result.getId())
                .isEqualTo(id);
        assertThat(result.getName())
                .isEqualTo(name);
        assertThat(result.getDescription())
                .isEqualTo(description);
    }

    @Test
    public void findByIdThrowsForNotExistingStartup() {
        when(startupRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findById(randomPositiveLong()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findAllFindsAllExistingStartups() {
        List<Startup> startups = new ArrayList<>();
        Startup startup1 = new Startup(1, "name1", "description1");
        Startup startup2 = new Startup(2, "name2", "description2");
        startups.add(startup1);
        startups.add(startup2);
        when(startupRepository.findAll())
                .thenReturn(new ArrayList<Startup>() {{
                    add(startup1);
                    add(startup2);
                }});
        List<Startup> result = underTest.findAll();
        assertThat(result).isInstanceOf(List.class);
        assertThat(result.size()).isEqualTo(startups.size());
        assertThat(result.get(0)).isInstanceOf(Startup.class);
        assertThat(result.get(0).getId()).isEqualTo(startup1.getId());
    }

    @Test
    public void findAllThrowsForNoExistingStartups() {
        when(startupRepository.findAll())
                .thenReturn(new ArrayList<Startup>());
        assertThatThrownBy(() -> underTest.findAll())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void findByNameReturnsStartupWithMatchingName() {
        Long id = randomPositiveLong();
        String name = "name";
        String description = "description";
        when(startupRepository.findByName(name))
                .thenReturn(Optional.of(new Startup(id, name, description)));

        Startup result = underTest.findByName(name);

        assertThat(result)
                .isInstanceOf(Startup.class);
        assertThat(result.getId())
                .isEqualTo(id);
        assertThat(result.getName())
                .isEqualTo(name);
        assertThat(result.getDescription())
                .isEqualTo(description);
    }

    @Test
    public void findByNameThrowsForNotExistingStartup() {
        when(startupRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> underTest.findByName(anyString()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Long randomPositiveLong() {
        long leftLimit = 1L;
        long rightLimit = 1000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
