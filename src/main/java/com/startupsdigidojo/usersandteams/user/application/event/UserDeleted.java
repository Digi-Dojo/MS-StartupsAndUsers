package com.startupsdigidojo.usersandteams.user.application.event;

import com.startupsdigidojo.usersandteams.user.domain.User;
import lombok.Getter;

public class UserDeleted extends UserEvent{
    public static final String USER_DELETED = "User Deleted";

    @Getter
    private String type = USER_DELETED;

    @Getter
    private User payload;

    public UserDeleted(User payload) {
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
