package com.startupsdigidojo.usersandteams.startup.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class that serves to manage actions performed on startups
 */
@Service
public class ManageStartups {

    private final StartupRepository startupRepository;

    @Autowired
    private StartupBroadcaster startupBroadcaster;

    @Autowired
    public ManageStartups(StartupRepository startupRepository) {
        this.startupRepository = startupRepository;
    }

    /**
     * Given a name and a description, this method creates a startup with said characteristics
     * @param name the name of the startup
     * @param description the description of the startup
     * @return the newly created startup
     * @throws IllegalArgumentException if a startup with the given name already exists
     */
    public Startup createStartup(String name, String description) {

        Optional<Startup> maybeStartup = startupRepository.findByName(name);

        if (maybeStartup.isPresent()) {
            throw new IllegalArgumentException("Startup with name " + name + " already exists");
        }

        Startup startup = new Startup(name, description);

        startupBroadcaster.emitNewStartup(startup);

        return startupRepository.save(startup);
    }

    /**
     * Given the current name of a startup and a new name, this method replaces the old name with the new one
     * @param oldName current name of the startup whose name we want to change
     * @param newName the name that should replace the current one
     * @return the newly updated startup
     * @throws IllegalArgumentException if a startup with the new name already exists, or if no startup with the old name is found
     */
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

    /**
     * Given the name of a startup, this method replaces its current description with the one provided
     * @param name name of the startup
     * @param description new description that will substitute the old
     * @return the newly updated startup
     * @throws IllegalArgumentException if no startup with the provided name is found
     */
    public Startup updateStartupDescription(String name, String description){
        Optional<Startup> maybeStartup = startupRepository.findByName(name);

        if(maybeStartup.isEmpty()){
            throw new IllegalArgumentException("Startup with name " + name + " does not exist");
        }

        Startup startup = maybeStartup.get();
        startup.setDescription(description);
        return startupRepository.save(startup);
    }

    /**
     *<p>Given the name of a startup, this methods deletes the instance of said startup from the database</p>
     * @param name name of the startup to be deleted
     * @throws IllegalArgumentException if no startup with such name is found
     */
    public void deleteStartup(String name){
        Optional<Startup> maybeStartup = startupRepository.findByName(name);

        if(maybeStartup.isEmpty()){
            throw new IllegalArgumentException("Startup with name " + name + " does not exist");
        }

        startupRepository.delete(maybeStartup.get());
    }
}
