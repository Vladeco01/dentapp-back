package com.license.dentapp.service;

import com.license.dentapp.dao.AppointmentRepository;
import com.license.dentapp.dao.UserRepository;
import com.license.dentapp.dto.AppointmentRequest;
import com.license.dentapp.dto.AppointmentResponse;
import com.license.dentapp.entity.Appointment;
import com.license.dentapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepo;
    private final UserRepository userRepo;

    public AppointmentService(AppointmentRepository appointmentRepo, UserRepository userRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
    }

    public void createAppointment(AppointmentRequest req, User client) {
        if (!Duration.between(req.getStartTime(), req.getEndTime()).equals(Duration.ofHours(1)))
            throw new RuntimeException("Programările trebuie să fie de 1 oră.");

        if (req.getStartTime().toLocalTime().isBefore(LocalTime.of(8, 0)) ||
                req.getEndTime().toLocalTime().isAfter(LocalTime.of(17, 0)))
            throw new RuntimeException("Programările trebuie să fie între 08:00 și 17:00.");

        boolean overlap = appointmentRepo.existsByDentistIdAndTimeOverlap(
                req.getDentistId(), req.getStartTime(), req.getEndTime());

        if (overlap) throw new RuntimeException("Intervalul este deja ocupat.");

        Appointment appointment = new Appointment();
        appointment.setStartTime(req.getStartTime());
        appointment.setEndTime(req.getEndTime());
        appointment.setStatus(req.isBlock() ? "BLOCKED" : "WAITING");
        appointment.setDentist(userRepo.findById(req.getDentistId()).orElseThrow());
        appointment.setClient(client);

        appointmentRepo.save(appointment);
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

    public void deleteAppointment(Integer id){
        appointmentRepo.deleteById(id);
    }


    public Appointment save(Appointment theAppointment) {
        return appointmentRepo.save(theAppointment);
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepo.findAll();
    }
}
