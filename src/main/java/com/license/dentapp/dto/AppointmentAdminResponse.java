package com.license.dentapp.dto;

import com.license.dentapp.entity.Appointment;

import java.time.LocalDateTime;

public class AppointmentAdminResponse {
    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String description;
    private Integer dentistId;
    private Integer clientId;

    public AppointmentAdminResponse() {
    }

    public AppointmentAdminResponse(Integer id,
                                    LocalDateTime startTime,
                                    LocalDateTime endTime,
                                    String status,
                                    String description,
                                    Integer dentistId,
                                    Integer clientId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.description = description;
        this.dentistId = dentistId;
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

    public Integer getDentistId() {
        return dentistId;
    }

    public void setDentistId(Integer dentistId) {
        this.dentistId = dentistId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public static AppointmentAdminResponse fromEntity(Appointment appointment) {
        return new AppointmentAdminResponse(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus(),
                appointment.getDescription(),
                appointment.getDentist().getId(),
                appointment.getClient().getId()
        );
    }
}