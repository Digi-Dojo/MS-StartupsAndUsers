package com.startupsdigidojo.usersandteams.user.application.event;

import com.startupsdigidojo.usersandteams.user.domain.User;
import lombok.Getter;

public class UserLogIn extends UserEvent{
    public static final String USER_LOG_IN = "User Log In";

    @Getter
    private String type = USER_LOG_IN;

    @Getter
    private User payload;

    public UserLogIn(User payload) {
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
