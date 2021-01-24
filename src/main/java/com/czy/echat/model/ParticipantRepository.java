package com.czy.echat.model;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Online user warehouse, store online users
 */
@Component
public class ParticipantRepository {
    private Map<String, User> activeSessions = new ConcurrentHashMap<String, User>(); //Online user map, key: user name, value: user object
    public Map<String, User> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Map<String, User> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public void add(String name, User user){
        activeSessions.put(name, user);

    }

    public User remove(String name){
        return activeSessions.remove(name);
    }

    public boolean containsUserName(String name){
        return activeSessions.containsKey(name);
    }
}
