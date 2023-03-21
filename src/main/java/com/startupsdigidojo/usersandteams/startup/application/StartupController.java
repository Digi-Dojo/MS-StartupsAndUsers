package com.startupsdigidojo.usersandteams.startup.application;

import com.startupsdigidojo.usersandteams.startup.domain.ManageStartups;
import com.startupsdigidojo.usersandteams.startup.domain.SearchStartups;
import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/startup")
public class StartupController {
    private ManageStartups manageStartups;
    private SearchStartups searchStartups;

    @Autowired
    public StartupController(ManageStartups manageStartups, SearchStartups searchStartups){
        this.manageStartups = manageStartups;
        this.searchStartups = searchStartups;
    }

    @GetMapping("{id}")
    public Startup findById(@PathVariable("id") Long id) {return searchStartups.findOne(id);}

    @PostMapping
    public Startup createNewStartup(@RequestBody CreateStartupDTO dto){
        return manageStartups.createStartup(dto.getName(), dto.getDescription());
    }
}
