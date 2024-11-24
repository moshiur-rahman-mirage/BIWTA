package com.zaberp.zab.biwtabackend.util;

import jakarta.persistence.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Column(name = "zauserid", updatable = false)
    private String zauserid;

    @Column(name = "zuuserid")
    private String zuuserid;

    @Column(name="ztime")
    private LocalDateTime ztime;

    @Column(name="zutime")
    private LocalDateTime zutime;


    @PrePersist
    protected void onCreate() {
        // Set zauserid on insert
        this.zauserid = getCurrentUserId();
        this.ztime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        // Set zuuserid on update
        this.zuuserid = getCurrentUserId();
        this.zutime = LocalDateTime.now();
    }

    // Replace this method to fetch the logged-in user's ID
    private String getCurrentUserId() {
        // Fetch logged-in user ID from security context or session
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Getters and setters
    public String getZauserid() {
        return zauserid;
    }

    public void setZauserid(String zauserid) {
        this.zauserid = zauserid;
    }

    public String getZuuserid() {
        return zuuserid;
    }

    public void setZuuserid(String zuuserid) {
        this.zuuserid = zuuserid;
    }
}
