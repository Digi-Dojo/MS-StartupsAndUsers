package com.startupsdigidojo.usersandteams.user.application.event;

import com.startupsdigidojo.usersandteams.user.domain.User;
import lombok.Getter;

public class UserCreated extends UserEvent {
    public static final String USER_CREATED = "User Created";

    @Getter
    private String type = USER_CREATED;

    @Getter
    private User payload;

    public UserCreated(User payload) {
        this.payload = payload;
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
