package com.startupsdigidojo.usersandteams.startup.application.event;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import lombok.Getter;

public class StartupDescriptionUpdate {

    private static final String STARTUP_DESCRIPTION_UPDATE = "Startup Description Update";

    @Getter
    private String type = STARTUP_DESCRIPTION_UPDATE;

    @Getter
    private Startup payload;

    public StartupDescriptionUpdate(Startup payload){this.payload = payload;}

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