package com.startupsdigidojo.usersandteams.domain.Startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManageStartups {

    private StartupRepository startupRepository;

    @Autowired
    public ManageStartups(StartupRepository startupRepository) {
        this.startupRepository = startupRepository;
    }

    public Startup createStartup(String name, String description) {

        Optional<Startup> maybeStartup = startupRepository.findByName(name);

        if (maybeStartup.isPresent()) {
            throw new IllegalArgumentException("Startup with name " + name + " already exists");
        }

        return startupRepository.save(new Startup(name, description));
    }

    public Startup updateStartupName(String oldName, String newName){
        Optional<Startup> maybeStartup = startupRepository.findByName(newName);

        if (maybeStartup.isPresent()) {
            throw new IllegalArgumentException("Startup with name " + newName + " already exists");
        }

        maybeStartup = startupRepository.findByName(oldName);
        if(maybeStartup.isEmpty()){
            throw new IllegalArgumentException("Startup with name " + oldName + " does not exist");
        }
        Startup startup = maybeStartup.get();
        startup.setName(newName);
        return startupRepository.save(startup);
    }

    public Startup updateStartupDescription(String name, String description){
        Optional<Startup> maybeStartup = startupRepository.findByName(name);

        if(maybeStartup.isEmpty()){
            throw new IllegalArgumentException("Startup with name " + name + " does not exist");
        }

        Startup startup = maybeStartup.get();
        startup.setDescription(description);
        return startupRepository.save(startup);
    }

    public void deleteStartup(String name){
        Optional<Startup> maybeStartup = startupRepository.findByName(name);

        if(maybeStartup.isEmpty()){
            throw new IllegalArgumentException("Startup with name " + name + " does not exist");
        }

        startupRepository.delete(maybeStartup.get());
    }
}
