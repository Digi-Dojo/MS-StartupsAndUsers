package com.startupsdigidojo.usersandteams.startup.domain;

public interface StartupBroadcaster {
    void emitNewStartup(Startup startup);       //used to throw the event
}
