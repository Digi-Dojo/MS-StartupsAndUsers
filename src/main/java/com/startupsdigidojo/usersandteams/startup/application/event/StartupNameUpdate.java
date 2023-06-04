package com.startupsdigidojo.usersandteams.startup.application.event;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import lombok.Getter;

public class StartupNameUpdate {

    private static final String STARTUP_NAME_UPDATE = "Startup Name Update";

    @Getter
    private String type = STARTUP_NAME_UPDATE;

    @Getter
    private Startup payload;

    public StartupNameUpdate(Startup payload){this.payload = payload;}

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