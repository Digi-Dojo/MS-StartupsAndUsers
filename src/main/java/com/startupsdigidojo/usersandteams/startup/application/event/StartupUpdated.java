package com.startupsdigidojo.usersandteams.startup.application.event;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import lombok.Getter;

public class StartupUpdated {

    private static final String STARTUP_UPDATED = "Startup updated";

    @Getter
    private String type = STARTUP_UPDATED;

    @Getter
    private Startup payload;

    public StartupUpdated(Startup payload){this.payload = payload;}

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