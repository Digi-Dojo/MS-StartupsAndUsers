package com.startupsdigidojo.usersandteams.startup.application.event;

import com.startupsdigidojo.usersandteams.startup.domain.Startup;
import lombok.Getter;

public class NewStartup {

    private static final String NEW_STARTUP = "New Startup";

    @Getter
    private String type = NEW_STARTUP;

    @Getter
    private Startup payload;

    public NewStartup(Startup payload){this.payload = payload;}

    public String toJson(){
        return "{" +
                "\"type\": \"" + type + "\"," +
                "\"time\": \"" + System.currentTimeMillis() + "\"" +
                "}" +
                "}";
    }
}