package com.lukaszplawiak.projectapp.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.time.Clock;
import java.time.LocalDateTime;

@Embeddable
public class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    @Transient
    private Clock clock = Clock.systemDefaultZone();

    public Audit() {
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now(clock).withSecond(0).withNano(0);
    }
    @PreUpdate
    void preMerge() {
        updatedOn = LocalDateTime.now(clock).withSecond(0).withNano(0);
    }
}