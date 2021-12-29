package com.lukaszplawiak.projectapp.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
public class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now().withNano(0);
    }
    @PreUpdate
    void preMerge() {
        updatedOn = LocalDateTime.now().withNano(0);
    }
}
