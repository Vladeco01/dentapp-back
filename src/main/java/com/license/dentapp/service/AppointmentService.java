package com.license.dentapp.service;

import com.license.dentapp.dao.AppointmentRepository;
import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.dto.AppointmentRequest;
import com.license.dentapp.dto.AppointmentResponse;
import com.license.dentapp.entity.Appointment;
import com.license.dentapp.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final UserRepository userRepo;

    public AppointmentService(AppointmentRepository appointmentRepo, UserRepository userRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
    }

    public void createAppointment(AppointmentRequest req) {
        LocalDateTime start = req.getStartTime();
        if (start == null) {
            throw new RuntimeException("Trebuie specificat startTime.");
        }
        LocalDateTime end = (req.getEndTime() != null)
                ? req.getEndTime()
                : start.plusHours(1);

        if (!Duration.between(start, end).equals(Duration.ofHours(1))) {
            throw new RuntimeException("Programările trebuie să fie de 1 oră.");
        }

        if (start.toLocalTime().isBefore(LocalTime.of(8, 0)) ||
                end.toLocalTime().isAfter(LocalTime.of(17, 0))) {
            throw new RuntimeException("Programările trebuie să fie între 08:00 și 17:00.");
        }

        boolean overlap = appointmentRepo.existsByDentistIdAndTimeOverlap(
                req.getDentistId(), start, end
        );
        if (overlap) {
            throw new RuntimeException("Intervalul este deja ocupat.");
        }

        User client = userRepo.findById(req.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Utilizator nu exista"));

        Appointment appt = new Appointment();
        appt.setStartTime(start);
        appt.setEndTime(end);
        appt.setStatus(req.isBlock() ? "BLOCKED" : "WAITING");
        appt.setDescription(req.getDescription());
        appt.setDentist(userRepo.findById(req.getDentistId()).orElseThrow());
        appt.setClient(client);

        appointmentRepo.save(appt);
    }


    public void updateStatus(Integer id, String status) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow();
        appointment.setStatus(status);
        appointmentRepo.save(appointment);
    }

    public List<AppointmentResponse> getAppointmentsByDentist(Integer dentistId) {
        List<Appointment> appointments = appointmentRepo.findAllByDentistId(dentistId);

        return appointments.stream()
                .map(AppointmentResponse::fromEntity)
                .toList();
    }

    public List<AppointmentResponse> getAppointmentsByUserId(Integer userId) {
        List<Appointment> appointments = appointmentRepo.findAllByClientId(userId);

        return appointments.stream()
                .map(AppointmentResponse::fromEntity)
                .toList();
    }

    public List<AppointmentResponse> getPendingAppointmentsByDentist(Integer dentistId) {
        return appointmentRepo
                .findAllByDentistIdAndStatus(dentistId, "PENDING")
                .stream()
                .map(AppointmentResponse::fromEntity)
                .toList();
    }

    public void deleteAppointment(Integer id){
        appointmentRepo.deleteById(id);
    }


    public Appointment save(Appointment theAppointment) {
        return appointmentRepo.save(theAppointment);
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepo.findAll();
    }

    public List<LocalDateTime> getAvailableSlots(Integer dentistId) {

        List<Appointment> appointments = appointmentRepo.findAllByDentistId(dentistId);

        Set<LocalDateTime> taken = appointments.stream()
                .map(Appointment::getStartTime)
                .collect(Collectors.toSet());


        List<LocalDateTime> freeSlots = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int dayOffset = 0; dayOffset < 7; dayOffset++) {
            LocalDate date = today.plusDays(dayOffset);
            for (int hour = 8; hour < 17; hour++) {
                LocalDateTime slot = date.atTime(hour, 0);
                if (!taken.contains(slot)) {
                    freeSlots.add(slot);
                }
            }
        }
        return freeSlots;
    }
}
