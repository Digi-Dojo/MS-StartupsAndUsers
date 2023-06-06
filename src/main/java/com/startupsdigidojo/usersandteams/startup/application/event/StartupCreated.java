package com.startupsdigidojo.usersandteams.startup.application.event;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import lombok.Getter;

public class StartupCreated {

    private static final String STARTUP_CREATED = "Startup created";

    @Getter
    private String type = STARTUP_CREATED;

    @Getter
    private Startup payload;

    public StartupCreated(Startup payload){this.payload = payload;}

    public String toJson(){
        return "{" +
                    "\"type\": \"" + type + "\"," +
                    "\"payload\": {" +
                        "\"id\": \"" + payload.getId() + "\"," +
                        "\"name\": \"" + payload.getName() + "\"," +
                        "\"description\": \"" + payload.getDescription() + "\"," +
                        "\"time\": \"" + System.currentTimeMillis() + "\"" +
                    "}" +
                "}";
    }
}