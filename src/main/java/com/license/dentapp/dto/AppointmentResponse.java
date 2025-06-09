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

    public AppointmentResponse(Integer id,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               String status,
                               String description,
                               String dentistName) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.description = description;
        this.dentistName = dentistName;
    }

    public static AppointmentResponse fromEntity(Appointment a) {
        String dentistName = a.getDentist().getFirstName() + " " + a.getDentist().getLastName();
        return new AppointmentResponse(
                a.getId(),
                a.getStartTime(),
                a.getEndTime(),
                a.getStatus(),
                a.getDescription(),
                dentistName
        );
    }
}
