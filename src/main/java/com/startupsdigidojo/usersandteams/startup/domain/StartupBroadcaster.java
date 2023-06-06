package com.startupsdigidojo.usersandteams.startup.domain;

public interface StartupBroadcaster {
    void emitStartupCreated(Startup startup);       //used to throw the event

    void emitStartupUpdated(Startup startup);

    void emitStartupDeleted(Startup startup);
}
