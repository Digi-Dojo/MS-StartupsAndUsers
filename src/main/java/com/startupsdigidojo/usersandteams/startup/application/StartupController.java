package com.startupsdigidojo.usersandteams.startup.application;

import com.startupsdigidojo.usersandteams.startup.domain.ManageStartups;
import com.startupsdigidojo.usersandteams.startup.domain.SearchStartups;
import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://mfront-startupsandusers.onrender.com/")
@RestController
@RequestMapping(path = "/v1/startup")
public class StartupController {
    private final ManageStartups manageStartups;
    private final SearchStartups searchStartups;

    @Autowired
    public StartupController(ManageStartups manageStartups, SearchStartups searchStartups){
        this.manageStartups = manageStartups;
        this.searchStartups = searchStartups;
    }

    @GetMapping("/{id}")
    public Startup findById(@PathVariable("id") Long id) {return searchStartups.findById(id);}

    @GetMapping("/name/{name}")
    public Startup findByName(@PathVariable("name") String name) {return searchStartups.findByName(name);}

    @PostMapping("/create")
    public Startup createNewStartup(@RequestBody CreateStartupDTO dto){
        return manageStartups.createStartup(dto.getName(), dto.getDescription());
    }

    @PostMapping("/updateName")
    public Startup updateStartupName(@RequestBody UpdateStartupNameDTO nameDTO) {
        return manageStartups.updateStartupName(nameDTO.getOldName(), nameDTO.getNewName());
    }

    @PostMapping("/updateDescription")
    public Startup updateStartupDescription(@RequestBody UpdateStartupDescriptionDTO descriptionDTO) {
        return manageStartups.updateStartupDescription(descriptionDTO.getName(), descriptionDTO.getDescription());
    }

    @GetMapping("/getAll")
    public List<Startup> findAll(){
        return searchStartups.findAll();
    }

    @DeleteMapping("/delete")
    public void deleteStartup(@RequestBody DeleteStartupDTO deleteDTO) {
        manageStartups.deleteStartup(deleteDTO.getName());
    }
}
