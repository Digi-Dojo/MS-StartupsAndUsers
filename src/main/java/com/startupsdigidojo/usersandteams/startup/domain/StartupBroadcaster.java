package com.startupsdigidojo.usersandteams.startup.domain;

public interface StartupBroadcaster {
    void emitNewStartup(Startup startup);       //used to throw the event

    void emitStartupNameUpdate(Startup startup);

    void emitStartupDescriptionUpdate(Startup startup);

    void emitStartupDelete(Startup startup);
}
