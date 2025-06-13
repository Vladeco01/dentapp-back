package com.license.dentapp.dto;

import java.time.LocalDateTime;

public class BlockSlotRequest {
    private Integer dentistId;
    private LocalDateTime startTime;

    public Integer getDentistId() {
        return dentistId;
    }

    public void setDentistId(Integer dentistId) {
        this.dentistId = dentistId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}