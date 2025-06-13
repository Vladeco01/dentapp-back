package com.license.dentapp.dto;

import com.license.dentapp.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String description;
    private String dentistName;
    private Integer clientId;

    public AppointmentResponse(Integer id,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               String status,
                               String description,
                               String dentistName,
                               Integer clientId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.description = description;
        this.dentistName = dentistName;
        this.clientId = clientId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public static AppointmentResponse fromEntity(Appointment a) {
        String dentistName = a.getDentist().getFirstName() + " " + a.getDentist().getLastName();
        return new AppointmentResponse(
                a.getId(),
                a.getStartTime(),
                a.getEndTime(),
                a.getStatus(),
                a.getDescription(),
                dentistName,
                a.getClient().getId()
        );
    }
}
