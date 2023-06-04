package com.startupsdigidojo.usersandteams.startup.application.event;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import lombok.Getter;

public class StartupDelete {

    private static final String STARTUP_DELETE = "Startup delete";

    @Getter
    private String type = STARTUP_DELETE;

    @Getter
    private Startup payload;

    public StartupDelete(Startup payload){this.payload = payload;}

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