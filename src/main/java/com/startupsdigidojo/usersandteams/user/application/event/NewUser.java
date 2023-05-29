package com.startupsdigidojo.usersandteams.user.application.event;

import com.startupsdigidojo.usersandteams.user.domain.User;
import lombok.Getter;

public class NewUser extends Event{
    public static final String NEW_USER = "New User";

    @Getter
    private String type = NEW_USER;

    @Getter
    private User payload;

    public NewUser(User payload) {
        this.payload = payload;
    }

    public String toJson(){
        return "{" +
                    "\"type\": \"" + type + "\"," +
                    "\"payload\": {" +
                        "\"uuid\": \"" + payload.getId() + "\"," +
                        "\"name\": \"" + payload.getName() + "\"," +
                        "\"mailAddress\": \"" + payload.getMailAddress() + "\"," +
                        "\"time\": \"" + System.currentTimeMillis() + "\"" +
                    "}" +
                "}";
    }
}
