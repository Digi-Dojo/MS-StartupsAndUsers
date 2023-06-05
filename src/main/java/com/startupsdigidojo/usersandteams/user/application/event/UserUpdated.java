package com.startupsdigidojo.usersandteams.user.application.event;

import com.startupsdigidojo.usersandteams.user.domain.User;
import lombok.Getter;

public class UserUpdated extends UserEvent{
    public static final String USER_UPDATED = "User Updated";

    @Getter
    private String type = USER_UPDATED;

    @Getter
    private User payload;

    public UserUpdated(User payload, String attribute) {
        this.payload = payload;
        type += " " + attribute;
    }

    public String toJson(){
        return "{" +
                "\"type\": \"" + type + "\"," +
                "\"payload\": {" +
                "\"id\": \"" + payload.getId() + "\"," +
                "\"name\": \"" + payload.getName() + "\"," +
                "\"mailAddress\": \"" + payload.getMailAddress() + "\"," +
                "\"time\": \"" + System.currentTimeMillis() + "\"" +
                "}" +
                "}";
    }
}
