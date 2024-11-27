package com.zaberp.zab.biwtabackend.util;

import jakarta.persistence.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.lang.reflect.Field;
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
        System.out.println("onCreate invoked: zauserid = " + zauserid + ", ztime = " + ztime);
    }

    @PreUpdate
    protected void onUpdate() {
        // Set zuuserid on update
        this.zuuserid = getCurrentUserId();
        this.zutime = LocalDateTime.now();
        System.out.println("onUpdat3e invoked: zuuserid = " + zuuserid + ", zutime = " + zutime);
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

    public void initializeDefaults() {
        // Get all fields of the class
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Allow access to private fields
            try {
                Object value = field.get(this); // Get current value of the field
                if (value == null) {
                    // Check the field type and assign default values
                    if (field.getType() == Integer.class) {
                        field.set(this, 0);
                    } else if (field.getType() == Double.class) {
                        field.set(this, 0.0);
                    } else if (field.getType() == Long.class) {
                        field.set(this, 0L);
                    } else if (field.getType() == Float.class) {
                        field.set(this, 0f);
                    } else if (field.getType() == String.class) {
                        field.set(this, "");
                    }
                }
            } catch (IllegalAccessException e) {
                System.err.println("Error initializing field: " + field.getName());
                e.printStackTrace();
            }
        }
    }
}
